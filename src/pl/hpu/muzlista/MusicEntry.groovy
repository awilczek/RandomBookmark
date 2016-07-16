package pl.hpu.muzlista

class MusicEntry {

    final url
    final name
    final catalog

    public MusicEntry(url, String name, String catalog) {
        this.url = url
        this.name = name.replace(' - YouTube', '').replace('▶ ', '').trim()
        this.catalog = catalog;
    }

    public MusicEntry(url) {
        this.url = url
    }

    @Override
    public String toString() {
        '<a href="' + url + '" target="_' + name + '">' + name + '</a>'
    }
}
