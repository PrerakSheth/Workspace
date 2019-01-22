package com.konkr.Models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicTracks {

    /**
     * href : https://api.spotify.com/v1/playlists/7BOx5R9u4r3dEgMk84oipk/tracks?offset=0&limit=100
     * items : [{"added_at":"2018-08-27T11:40:03Z","added_by":{"external_urls":{"spotify":"https://open.spotify.com/user/81yvxgpvml6zcyo50w7q029u3"},"href":"https://api.spotify.com/v1/users/81yvxgpvml6zcyo50w7q029u3","id":"81yvxgpvml6zcyo50w7q029u3","type":"user","uri":"spotify:user:81yvxgpvml6zcyo50w7q029u3"},"is_local":false,"primary_color":null,"track":{"album":{"album_type":"single","artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"},"href":"https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf","id":"1HBjj22wzbscIZ9sEb5dyf","name":"Jonas Blue","type":"artist","uri":"spotify:artist:1HBjj22wzbscIZ9sEb5dyf"},{"external_urls":{"spotify":"https://open.spotify.com/artist/1INuLZXjjVbcJRyWvD1iSq"},"href":"https://api.spotify.com/v1/artists/1INuLZXjjVbcJRyWvD1iSq","id":"1INuLZXjjVbcJRyWvD1iSq","name":"Jack & Jack","type":"artist","uri":"spotify:artist:1INuLZXjjVbcJRyWvD1iSq"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"external_urls":{"spotify":"https://open.spotify.com/album/3KjCdhPbjbLptyJzviKu4P"},"href":"https://api.spotify.com/v1/albums/3KjCdhPbjbLptyJzviKu4P","id":"3KjCdhPbjbLptyJzviKu4P","images":[{"height":640,"url":"https://i.scdn.co/image/24686f4ddeb3be3f291e72e5b060c2f5ec7a124a","width":640},{"height":300,"url":"https://i.scdn.co/image/d3d0ad4060e18d90a4a8f788cc717813da96b7ad","width":300},{"height":64,"url":"https://i.scdn.co/image/2f9760ad0b7f23744ffad9d215638c847b61baff","width":64}],"name":"Rise","release_date":"2018-05-25","release_date_precision":"day","total_tracks":1,"type":"album","uri":"spotify:album:3KjCdhPbjbLptyJzviKu4P"},"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"},"href":"https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf","id":"1HBjj22wzbscIZ9sEb5dyf","name":"Jonas Blue","type":"artist","uri":"spotify:artist:1HBjj22wzbscIZ9sEb5dyf"},{"external_urls":{"spotify":"https://open.spotify.com/artist/1INuLZXjjVbcJRyWvD1iSq"},"href":"https://api.spotify.com/v1/artists/1INuLZXjjVbcJRyWvD1iSq","id":"1INuLZXjjVbcJRyWvD1iSq","name":"Jack & Jack","type":"artist","uri":"spotify:artist:1INuLZXjjVbcJRyWvD1iSq"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"disc_number":1,"duration_ms":194407,"episode":false,"explicit":false,"external_ids":{"isrc":"GBUM71802109"},"external_urls":{"spotify":"https://open.spotify.com/track/3u1S1OmAUhx5DRlLrXqyp3"},"href":"https://api.spotify.com/v1/tracks/3u1S1OmAUhx5DRlLrXqyp3","id":"3u1S1OmAUhx5DRlLrXqyp3","is_local":false,"name":"Rise","popularity":94,"preview_url":null,"track":true,"track_number":1,"type":"track","uri":"spotify:track:3u1S1OmAUhx5DRlLrXqyp3"},"video_thumbnail":{"url":null}},{"added_at":"2018-08-27T11:40:05Z","added_by":{"external_urls":{"spotify":"https://open.spotify.com/user/81yvxgpvml6zcyo50w7q029u3"},"href":"https://api.spotify.com/v1/users/81yvxgpvml6zcyo50w7q029u3","id":"81yvxgpvml6zcyo50w7q029u3","type":"user","uri":"spotify:user:81yvxgpvml6zcyo50w7q029u3"},"is_local":false,"primary_color":null,"track":{"album":{"album_type":"single","artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/7keGfmQR4X5w0two1xKZ7d"},"href":"https://api.spotify.com/v1/artists/7keGfmQR4X5w0two1xKZ7d","id":"7keGfmQR4X5w0two1xKZ7d","name":"Kungs","type":"artist","uri":"spotify:artist:7keGfmQR4X5w0two1xKZ7d"},{"external_urls":{"spotify":"https://open.spotify.com/artist/7KUri7klyLaIFXLcuuOMCd"},"href":"https://api.spotify.com/v1/artists/7KUri7klyLaIFXLcuuOMCd","id":"7KUri7klyLaIFXLcuuOMCd","name":"Stargate","type":"artist","uri":"spotify:artist:7KUri7klyLaIFXLcuuOMCd"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"external_urls":{"spotify":"https://open.spotify.com/album/2b9FyXvupIJe3lFCc8TGOv"},"href":"https://api.spotify.com/v1/albums/2b9FyXvupIJe3lFCc8TGOv","id":"2b9FyXvupIJe3lFCc8TGOv","images":[{"height":640,"url":"https://i.scdn.co/image/8bc9f25bf2f37a559615fe849f5386ba8523e3e4","width":640},{"height":300,"url":"https://i.scdn.co/image/6799f94ec43013cd381b6608a1ce8f3422143cff","width":300},{"height":64,"url":"https://i.scdn.co/image/a9e819c7aeb73c3df41b912ad40c4ed399580573","width":64}],"name":"Be Right Here","release_date":"2018-06-22","release_date_precision":"day","total_tracks":1,"type":"album","uri":"spotify:album:2b9FyXvupIJe3lFCc8TGOv"},"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/7keGfmQR4X5w0two1xKZ7d"},"href":"https://api.spotify.com/v1/artists/7keGfmQR4X5w0two1xKZ7d","id":"7keGfmQR4X5w0two1xKZ7d","name":"Kungs","type":"artist","uri":"spotify:artist:7keGfmQR4X5w0two1xKZ7d"},{"external_urls":{"spotify":"https://open.spotify.com/artist/7KUri7klyLaIFXLcuuOMCd"},"href":"https://api.spotify.com/v1/artists/7KUri7klyLaIFXLcuuOMCd","id":"7KUri7klyLaIFXLcuuOMCd","name":"Stargate","type":"artist","uri":"spotify:artist:7KUri7klyLaIFXLcuuOMCd"},{"external_urls":{"spotify":"https://open.spotify.com/artist/6wUAn24MOirNVNqQM47fda"},"href":"https://api.spotify.com/v1/artists/6wUAn24MOirNVNqQM47fda","id":"6wUAn24MOirNVNqQM47fda","name":"GOLDN","type":"artist","uri":"spotify:artist:6wUAn24MOirNVNqQM47fda"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"disc_number":1,"duration_ms":191000,"episode":false,"explicit":false,"external_ids":{"isrc":"FR9W11811453"},"external_urls":{"spotify":"https://open.spotify.com/track/6Yk4HTvs1Ww41l4c7iyBlH"},"href":"https://api.spotify.com/v1/tracks/6Yk4HTvs1Ww41l4c7iyBlH","id":"6Yk4HTvs1Ww41l4c7iyBlH","is_local":false,"name":"Be Right Here","popularity":70,"preview_url":null,"track":true,"track_number":1,"type":"track","uri":"spotify:track:6Yk4HTvs1Ww41l4c7iyBlH"},"video_thumbnail":{"url":null}},{"added_at":"2018-08-28T09:59:59Z","added_by":{"external_urls":{"spotify":"https://open.spotify.com/user/81yvxgpvml6zcyo50w7q029u3"},"href":"https://api.spotify.com/v1/users/81yvxgpvml6zcyo50w7q029u3","id":"81yvxgpvml6zcyo50w7q029u3","type":"user","uri":"spotify:user:81yvxgpvml6zcyo50w7q029u3"},"is_local":false,"primary_color":null,"track":{"album":{"album_type":"single","artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/4tZwfgrHOc3mvqYlEYSvVi"},"href":"https://api.spotify.com/v1/artists/4tZwfgrHOc3mvqYlEYSvVi","id":"4tZwfgrHOc3mvqYlEYSvVi","name":"Daft Punk","type":"artist","uri":"spotify:artist:4tZwfgrHOc3mvqYlEYSvVi"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"external_urls":{"spotify":"https://open.spotify.com/album/2ePFIvZKMe8zefATp9ofFA"},"href":"https://api.spotify.com/v1/albums/2ePFIvZKMe8zefATp9ofFA","id":"2ePFIvZKMe8zefATp9ofFA","images":[{"height":640,"url":"https://i.scdn.co/image/6361e3a93b9fb16ecf9aa357f16ef2ab2abd45fe","width":640},{"height":300,"url":"https://i.scdn.co/image/724740e052967cd57b59510c90eb2720ab2d07f5","width":300},{"height":64,"url":"https://i.scdn.co/image/695754f7dfaf3f8ad3c22e4728592163aa000cba","width":64}],"name":"Get Lucky (feat. Pharrell Williams & Nile Rodgers) [Radio Edit]","release_date":"2013-04-19","release_date_precision":"day","total_tracks":1,"type":"album","uri":"spotify:album:2ePFIvZKMe8zefATp9ofFA"},"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/4tZwfgrHOc3mvqYlEYSvVi"},"href":"https://api.spotify.com/v1/artists/4tZwfgrHOc3mvqYlEYSvVi","id":"4tZwfgrHOc3mvqYlEYSvVi","name":"Daft Punk","type":"artist","uri":"spotify:artist:4tZwfgrHOc3mvqYlEYSvVi"},{"external_urls":{"spotify":"https://open.spotify.com/artist/2RdwBSPQiwcmiDo9kixcl8"},"href":"https://api.spotify.com/v1/artists/2RdwBSPQiwcmiDo9kixcl8","id":"2RdwBSPQiwcmiDo9kixcl8","name":"Pharrell Williams","type":"artist","uri":"spotify:artist:2RdwBSPQiwcmiDo9kixcl8"},{"external_urls":{"spotify":"https://open.spotify.com/artist/3yDIp0kaq9EFKe07X1X2rz"},"href":"https://api.spotify.com/v1/artists/3yDIp0kaq9EFKe07X1X2rz","id":"3yDIp0kaq9EFKe07X1X2rz","name":"Nile Rodgers","type":"artist","uri":"spotify:artist:3yDIp0kaq9EFKe07X1X2rz"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"disc_number":1,"duration_ms":248413,"episode":false,"explicit":false,"external_ids":{"isrc":"USQX91300809"},"external_urls":{"spotify":"https://open.spotify.com/track/2Foc5Q5nqNiosCNqttzHof"},"href":"https://api.spotify.com/v1/tracks/2Foc5Q5nqNiosCNqttzHof","id":"2Foc5Q5nqNiosCNqttzHof","is_local":false,"name":"Get Lucky (feat. Pharrell Williams & Nile Rodgers) - Radio Edit","popularity":75,"preview_url":"https://p.scdn.co/mp3-preview/92d40a2ae211cceb264e9ee1e67fe05f4d788200?cid=3dee1604f35748c9bfa31b94b59a450e","track":true,"track_number":1,"type":"track","uri":"spotify:track:2Foc5Q5nqNiosCNqttzHof"},"video_thumbnail":{"url":null}}]
     * limit : 100
     * next : null
     * offset : 0
     * previous : null
     * total : 3
     */

    private String href;
    private int limit;
    private Object next;
    private int offset;
    private Object previous;
    private int total;
    private List<ItemsBean> items;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * added_at : 2018-08-27T11:40:03Z
         * added_by : {"external_urls":{"spotify":"https://open.spotify.com/user/81yvxgpvml6zcyo50w7q029u3"},"href":"https://api.spotify.com/v1/users/81yvxgpvml6zcyo50w7q029u3","id":"81yvxgpvml6zcyo50w7q029u3","type":"user","uri":"spotify:user:81yvxgpvml6zcyo50w7q029u3"}
         * is_local : false
         * primary_color : null
         * track : {"album":{"album_type":"single","artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"},"href":"https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf","id":"1HBjj22wzbscIZ9sEb5dyf","name":"Jonas Blue","type":"artist","uri":"spotify:artist:1HBjj22wzbscIZ9sEb5dyf"},{"external_urls":{"spotify":"https://open.spotify.com/artist/1INuLZXjjVbcJRyWvD1iSq"},"href":"https://api.spotify.com/v1/artists/1INuLZXjjVbcJRyWvD1iSq","id":"1INuLZXjjVbcJRyWvD1iSq","name":"Jack & Jack","type":"artist","uri":"spotify:artist:1INuLZXjjVbcJRyWvD1iSq"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"external_urls":{"spotify":"https://open.spotify.com/album/3KjCdhPbjbLptyJzviKu4P"},"href":"https://api.spotify.com/v1/albums/3KjCdhPbjbLptyJzviKu4P","id":"3KjCdhPbjbLptyJzviKu4P","images":[{"height":640,"url":"https://i.scdn.co/image/24686f4ddeb3be3f291e72e5b060c2f5ec7a124a","width":640},{"height":300,"url":"https://i.scdn.co/image/d3d0ad4060e18d90a4a8f788cc717813da96b7ad","width":300},{"height":64,"url":"https://i.scdn.co/image/2f9760ad0b7f23744ffad9d215638c847b61baff","width":64}],"name":"Rise","release_date":"2018-05-25","release_date_precision":"day","total_tracks":1,"type":"album","uri":"spotify:album:3KjCdhPbjbLptyJzviKu4P"},"artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"},"href":"https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf","id":"1HBjj22wzbscIZ9sEb5dyf","name":"Jonas Blue","type":"artist","uri":"spotify:artist:1HBjj22wzbscIZ9sEb5dyf"},{"external_urls":{"spotify":"https://open.spotify.com/artist/1INuLZXjjVbcJRyWvD1iSq"},"href":"https://api.spotify.com/v1/artists/1INuLZXjjVbcJRyWvD1iSq","id":"1INuLZXjjVbcJRyWvD1iSq","name":"Jack & Jack","type":"artist","uri":"spotify:artist:1INuLZXjjVbcJRyWvD1iSq"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"disc_number":1,"duration_ms":194407,"episode":false,"explicit":false,"external_ids":{"isrc":"GBUM71802109"},"external_urls":{"spotify":"https://open.spotify.com/track/3u1S1OmAUhx5DRlLrXqyp3"},"href":"https://api.spotify.com/v1/tracks/3u1S1OmAUhx5DRlLrXqyp3","id":"3u1S1OmAUhx5DRlLrXqyp3","is_local":false,"name":"Rise","popularity":94,"preview_url":null,"track":true,"track_number":1,"type":"track","uri":"spotify:track:3u1S1OmAUhx5DRlLrXqyp3"}
         * video_thumbnail : {"url":null}
         */

        private String added_at;
        private AddedByBean added_by;
        private boolean is_local;
        private Object primary_color;
        private TrackBean track;
        private VideoThumbnailBean video_thumbnail;

        public String getAdded_at() {
            return added_at;
        }

        public void setAdded_at(String added_at) {
            this.added_at = added_at;
        }

        public AddedByBean getAdded_by() {
            return added_by;
        }

        public void setAdded_by(AddedByBean added_by) {
            this.added_by = added_by;
        }

        public boolean isIs_local() {
            return is_local;
        }

        public void setIs_local(boolean is_local) {
            this.is_local = is_local;
        }

        public Object getPrimary_color() {
            return primary_color;
        }

        public void setPrimary_color(Object primary_color) {
            this.primary_color = primary_color;
        }

        public TrackBean getTrack() {
            return track;
        }

        public void setTrack(TrackBean track) {
            this.track = track;
        }

        public VideoThumbnailBean getVideo_thumbnail() {
            return video_thumbnail;
        }

        public void setVideo_thumbnail(VideoThumbnailBean video_thumbnail) {
            this.video_thumbnail = video_thumbnail;
        }

        public static class AddedByBean {
            /**
             * external_urls : {"spotify":"https://open.spotify.com/user/81yvxgpvml6zcyo50w7q029u3"}
             * href : https://api.spotify.com/v1/users/81yvxgpvml6zcyo50w7q029u3
             * id : 81yvxgpvml6zcyo50w7q029u3
             * type : user
             * uri : spotify:user:81yvxgpvml6zcyo50w7q029u3
             */

            private ExternalUrlsBean external_urls;
            private String href;
            private String id;
            private String type;
            private String uri;

            public ExternalUrlsBean getExternal_urls() {
                return external_urls;
            }

            public void setExternal_urls(ExternalUrlsBean external_urls) {
                this.external_urls = external_urls;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public static class ExternalUrlsBean {
                /**
                 * spotify : https://open.spotify.com/user/81yvxgpvml6zcyo50w7q029u3
                 */

                private String spotify;

                public String getSpotify() {
                    return spotify;
                }

                public void setSpotify(String spotify) {
                    this.spotify = spotify;
                }
            }
        }

        public static class TrackBean {
            /**
             * album : {"album_type":"single","artists":[{"external_urls":{"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"},"href":"https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf","id":"1HBjj22wzbscIZ9sEb5dyf","name":"Jonas Blue","type":"artist","uri":"spotify:artist:1HBjj22wzbscIZ9sEb5dyf"},{"external_urls":{"spotify":"https://open.spotify.com/artist/1INuLZXjjVbcJRyWvD1iSq"},"href":"https://api.spotify.com/v1/artists/1INuLZXjjVbcJRyWvD1iSq","id":"1INuLZXjjVbcJRyWvD1iSq","name":"Jack & Jack","type":"artist","uri":"spotify:artist:1INuLZXjjVbcJRyWvD1iSq"}],"available_markets":["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"],"external_urls":{"spotify":"https://open.spotify.com/album/3KjCdhPbjbLptyJzviKu4P"},"href":"https://api.spotify.com/v1/albums/3KjCdhPbjbLptyJzviKu4P","id":"3KjCdhPbjbLptyJzviKu4P","images":[{"height":640,"url":"https://i.scdn.co/image/24686f4ddeb3be3f291e72e5b060c2f5ec7a124a","width":640},{"height":300,"url":"https://i.scdn.co/image/d3d0ad4060e18d90a4a8f788cc717813da96b7ad","width":300},{"height":64,"url":"https://i.scdn.co/image/2f9760ad0b7f23744ffad9d215638c847b61baff","width":64}],"name":"Rise","release_date":"2018-05-25","release_date_precision":"day","total_tracks":1,"type":"album","uri":"spotify:album:3KjCdhPbjbLptyJzviKu4P"}
             * artists : [{"external_urls":{"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"},"href":"https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf","id":"1HBjj22wzbscIZ9sEb5dyf","name":"Jonas Blue","type":"artist","uri":"spotify:artist:1HBjj22wzbscIZ9sEb5dyf"},{"external_urls":{"spotify":"https://open.spotify.com/artist/1INuLZXjjVbcJRyWvD1iSq"},"href":"https://api.spotify.com/v1/artists/1INuLZXjjVbcJRyWvD1iSq","id":"1INuLZXjjVbcJRyWvD1iSq","name":"Jack & Jack","type":"artist","uri":"spotify:artist:1INuLZXjjVbcJRyWvD1iSq"}]
             * available_markets : ["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"]
             * disc_number : 1
             * duration_ms : 194407
             * episode : false
             * explicit : false
             * external_ids : {"isrc":"GBUM71802109"}
             * external_urls : {"spotify":"https://open.spotify.com/track/3u1S1OmAUhx5DRlLrXqyp3"}
             * href : https://api.spotify.com/v1/tracks/3u1S1OmAUhx5DRlLrXqyp3
             * id : 3u1S1OmAUhx5DRlLrXqyp3
             * is_local : false
             * name : Rise
             * popularity : 94
             * preview_url : null
             * track : true
             * track_number : 1
             * type : track
             * uri : spotify:track:3u1S1OmAUhx5DRlLrXqyp3
             */

            private AlbumBean album;
            private int disc_number;
            private int duration_ms;
            private boolean episode;
            private boolean explicit;
            private ExternalIdsBean external_ids;
            private ExternalUrlsBeanXXX external_urls;
            private String href;
            private String id;
            private boolean is_local;
            private String name;
            private int popularity;
            private Object preview_url;
            private boolean track;
            private int track_number;
            private String type;
            private String uri;
            private List<ArtistsBeanX> artists;
            private List<String> available_markets;

            public AlbumBean getAlbum() {
                return album;
            }

            public void setAlbum(AlbumBean album) {
                this.album = album;
            }

            public int getDisc_number() {
                return disc_number;
            }

            public void setDisc_number(int disc_number) {
                this.disc_number = disc_number;
            }

            public int getDuration_ms() {
                return duration_ms;
            }

            public void setDuration_ms(int duration_ms) {
                this.duration_ms = duration_ms;
            }

            public boolean isEpisode() {
                return episode;
            }

            public void setEpisode(boolean episode) {
                this.episode = episode;
            }

            public boolean isExplicit() {
                return explicit;
            }

            public void setExplicit(boolean explicit) {
                this.explicit = explicit;
            }

            public ExternalIdsBean getExternal_ids() {
                return external_ids;
            }

            public void setExternal_ids(ExternalIdsBean external_ids) {
                this.external_ids = external_ids;
            }

            public ExternalUrlsBeanXXX getExternal_urls() {
                return external_urls;
            }

            public void setExternal_urls(ExternalUrlsBeanXXX external_urls) {
                this.external_urls = external_urls;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIs_local() {
                return is_local;
            }

            public void setIs_local(boolean is_local) {
                this.is_local = is_local;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPopularity() {
                return popularity;
            }

            public void setPopularity(int popularity) {
                this.popularity = popularity;
            }

            public Object getPreview_url() {
                return preview_url;
            }

            public void setPreview_url(Object preview_url) {
                this.preview_url = preview_url;
            }

            public boolean isTrack() {
                return track;
            }

            public void setTrack(boolean track) {
                this.track = track;
            }

            public int getTrack_number() {
                return track_number;
            }

            public void setTrack_number(int track_number) {
                this.track_number = track_number;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public List<ArtistsBeanX> getArtists() {
                return artists;
            }

            public void setArtists(List<ArtistsBeanX> artists) {
                this.artists = artists;
            }

            public List<String> getAvailable_markets() {
                return available_markets;
            }

            public void setAvailable_markets(List<String> available_markets) {
                this.available_markets = available_markets;
            }

            public static class AlbumBean {
                /**
                 * album_type : single
                 * artists : [{"external_urls":{"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"},"href":"https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf","id":"1HBjj22wzbscIZ9sEb5dyf","name":"Jonas Blue","type":"artist","uri":"spotify:artist:1HBjj22wzbscIZ9sEb5dyf"},{"external_urls":{"spotify":"https://open.spotify.com/artist/1INuLZXjjVbcJRyWvD1iSq"},"href":"https://api.spotify.com/v1/artists/1INuLZXjjVbcJRyWvD1iSq","id":"1INuLZXjjVbcJRyWvD1iSq","name":"Jack & Jack","type":"artist","uri":"spotify:artist:1INuLZXjjVbcJRyWvD1iSq"}]
                 * available_markets : ["AD","AR","AT","AU","BE","BG","BO","BR","CA","CH","CL","CO","CR","CY","CZ","DE","DK","DO","EC","EE","ES","FI","FR","GB","GR","GT","HK","HN","HU","ID","IE","IL","IS","IT","JP","LI","LT","LU","LV","MC","MT","MX","MY","NI","NL","NO","NZ","PA","PE","PH","PL","PT","PY","RO","SE","SG","SK","SV","TH","TR","TW","US","UY","VN","ZA"]
                 * external_urls : {"spotify":"https://open.spotify.com/album/3KjCdhPbjbLptyJzviKu4P"}
                 * href : https://api.spotify.com/v1/albums/3KjCdhPbjbLptyJzviKu4P
                 * id : 3KjCdhPbjbLptyJzviKu4P
                 * images : [{"height":640,"url":"https://i.scdn.co/image/24686f4ddeb3be3f291e72e5b060c2f5ec7a124a","width":640},{"height":300,"url":"https://i.scdn.co/image/d3d0ad4060e18d90a4a8f788cc717813da96b7ad","width":300},{"height":64,"url":"https://i.scdn.co/image/2f9760ad0b7f23744ffad9d215638c847b61baff","width":64}]
                 * name : Rise
                 * release_date : 2018-05-25
                 * release_date_precision : day
                 * total_tracks : 1
                 * type : album
                 * uri : spotify:album:3KjCdhPbjbLptyJzviKu4P
                 */

                private String album_type;
                private ExternalUrlsBeanX external_urls;
                private String href;
                private String id;
                private String name;
                private String release_date;
                private String release_date_precision;
                private int total_tracks;
                private String type;
                private String uri;
                private List<ArtistsBean> artists;
                private List<String> available_markets;
                private List<ImagesBean> images;

                public String getAlbum_type() {
                    return album_type;
                }

                public void setAlbum_type(String album_type) {
                    this.album_type = album_type;
                }

                public ExternalUrlsBeanX getExternal_urls() {
                    return external_urls;
                }

                public void setExternal_urls(ExternalUrlsBeanX external_urls) {
                    this.external_urls = external_urls;
                }

                public String getHref() {
                    return href;
                }

                public void setHref(String href) {
                    this.href = href;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getRelease_date() {
                    return release_date;
                }

                public void setRelease_date(String release_date) {
                    this.release_date = release_date;
                }

                public String getRelease_date_precision() {
                    return release_date_precision;
                }

                public void setRelease_date_precision(String release_date_precision) {
                    this.release_date_precision = release_date_precision;
                }

                public int getTotal_tracks() {
                    return total_tracks;
                }

                public void setTotal_tracks(int total_tracks) {
                    this.total_tracks = total_tracks;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUri() {
                    return uri;
                }

                public void setUri(String uri) {
                    this.uri = uri;
                }

                public List<ArtistsBean> getArtists() {
                    return artists;
                }

                public void setArtists(List<ArtistsBean> artists) {
                    this.artists = artists;
                }

                public List<String> getAvailable_markets() {
                    return available_markets;
                }

                public void setAvailable_markets(List<String> available_markets) {
                    this.available_markets = available_markets;
                }

                public List<ImagesBean> getImages() {
                    return images;
                }

                public void setImages(List<ImagesBean> images) {
                    this.images = images;
                }

                public static class ExternalUrlsBeanX {
                    /**
                     * spotify : https://open.spotify.com/album/3KjCdhPbjbLptyJzviKu4P
                     */

                    private String spotify;

                    public String getSpotify() {
                        return spotify;
                    }

                    public void setSpotify(String spotify) {
                        this.spotify = spotify;
                    }
                }

                public static class ArtistsBean {
                    /**
                     * external_urls : {"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"}
                     * href : https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf
                     * id : 1HBjj22wzbscIZ9sEb5dyf
                     * name : Jonas Blue
                     * type : artist
                     * uri : spotify:artist:1HBjj22wzbscIZ9sEb5dyf
                     */

                    private ExternalUrlsBeanXX external_urls;
                    private String href;
                    private String id;
                    private String name;
                    private String type;
                    private String uri;

                    public ExternalUrlsBeanXX getExternal_urls() {
                        return external_urls;
                    }

                    public void setExternal_urls(ExternalUrlsBeanXX external_urls) {
                        this.external_urls = external_urls;
                    }

                    public String getHref() {
                        return href;
                    }

                    public void setHref(String href) {
                        this.href = href;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public static class ExternalUrlsBeanXX {
                        /**
                         * spotify : https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf
                         */

                        private String spotify;

                        public String getSpotify() {
                            return spotify;
                        }

                        public void setSpotify(String spotify) {
                            this.spotify = spotify;
                        }
                    }
                }

                public static class ImagesBean {
                    /**
                     * height : 640
                     * url : https://i.scdn.co/image/24686f4ddeb3be3f291e72e5b060c2f5ec7a124a
                     * width : 640
                     */

                    private int height;
                    private String url;
                    private int width;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }
                }
            }

            public static class ExternalIdsBean {
                /**
                 * isrc : GBUM71802109
                 */

                private String isrc;

                public String getIsrc() {
                    return isrc;
                }

                public void setIsrc(String isrc) {
                    this.isrc = isrc;
                }
            }

            public static class ExternalUrlsBeanXXX {
                /**
                 * spotify : https://open.spotify.com/track/3u1S1OmAUhx5DRlLrXqyp3
                 */

                private String spotify;

                public String getSpotify() {
                    return spotify;
                }

                public void setSpotify(String spotify) {
                    this.spotify = spotify;
                }
            }

            public static class ArtistsBeanX {
                /**
                 * external_urls : {"spotify":"https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf"}
                 * href : https://api.spotify.com/v1/artists/1HBjj22wzbscIZ9sEb5dyf
                 * id : 1HBjj22wzbscIZ9sEb5dyf
                 * name : Jonas Blue
                 * type : artist
                 * uri : spotify:artist:1HBjj22wzbscIZ9sEb5dyf
                 */

                private ExternalUrlsBeanXXXX external_urls;
                private String href;
                private String id;
                private String name;
                private String type;
                private String uri;

                public ExternalUrlsBeanXXXX getExternal_urls() {
                    return external_urls;
                }

                public void setExternal_urls(ExternalUrlsBeanXXXX external_urls) {
                    this.external_urls = external_urls;
                }

                public String getHref() {
                    return href;
                }

                public void setHref(String href) {
                    this.href = href;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUri() {
                    return uri;
                }

                public void setUri(String uri) {
                    this.uri = uri;
                }

                public static class ExternalUrlsBeanXXXX {
                    /**
                     * spotify : https://open.spotify.com/artist/1HBjj22wzbscIZ9sEb5dyf
                     */

                    private String spotify;

                    public String getSpotify() {
                        return spotify;
                    }

                    public void setSpotify(String spotify) {
                        this.spotify = spotify;
                    }
                }
            }
        }

        public static class VideoThumbnailBean {
            /**
             * url : null
             */

            private Object url;

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }
        }
    }
}