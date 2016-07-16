package pl.hpu.muzlista

import groovy.json.JsonSlurper

class RandomPlayer {

    public static playRandom(inputFileName, musicSection) {
        def inputFile = new File(inputFileName)

        StringBuilder doc = new StringBuilder();
        for (String line : inputFile.readLines()) {
            doc.append(line)
        }

        def json = new JsonSlurper().parseText(doc.toString());

        List<MusicEntry> playlist = extractMusicEntries(json, musicSection)
        playlist.unique(true) { it.url }

        def randomIndex = new Random().nextInt(playlist.size());
        def randomUrl = playlist.get(randomIndex).getUrl();

        Runtime.getRuntime().exec(["google-chrome", randomUrl] as String[]);
    }

    private static extractMusicEntries(json, musicSection) {
        List<MusicEntry> result = new ArrayList()

        for (Map bookmarksBarElement : json.roots.bookmark_bar.children) {
           if (musicSection.equals(bookmarksBarElement.name)) {
               for (Map musicFolder : bookmarksBarElement.children) {
                   for (Map song : musicFolder.children) {
                       result.add(new MusicEntry(song.url))
                   }
               }
           }
        }

        result
    }

    public static void main(String[] args) {
        RandomPlayer.playRandom(args[0] + "/.config/google-chrome/Default/Bookmarks", args[1])
    }
}
