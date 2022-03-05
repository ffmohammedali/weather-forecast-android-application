
package com.example.weatherforecastapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WeatherPolutionModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("current")
        @Expose
        private Current current;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Current getCurrent() {
            return current;
        }

        public void setCurrent(Current current) {
            this.current = current;
        }

        public static class Current {

            @SerializedName("weather")
            @Expose
            private Weather weather;
            @SerializedName("pollution")
            @Expose
            private Pollution pollution;

            public Weather getWeather() {
                return weather;
            }

            public void setWeather(Weather weather) {
                this.weather = weather;
            }

            public Pollution getPollution() {
                return pollution;
            }

            public void setPollution(Pollution pollution) {
                this.pollution = pollution;
            }

            public static class Weather {

                @SerializedName("ts")
                @Expose
                private String ts;
                @SerializedName("tp")
                @Expose
                private Integer tp;
                @SerializedName("pr")
                @Expose
                private Integer pr;
                @SerializedName("hu")
                @Expose
                private Integer hu;
                @SerializedName("ws")
                @Expose
                private Double ws;
                @SerializedName("wd")
                @Expose
                private Integer wd;
                @SerializedName("ic")
                @Expose
                private String ic;

                public String getTs() {
                    return ts;
                }

                public void setTs(String ts) {
                    this.ts = ts;
                }

                public Integer getTp() {
                    return tp;
                }

                public void setTp(Integer tp) {
                    this.tp = tp;
                }

                public Integer getPr() {
                    return pr;
                }

                public void setPr(Integer pr) {
                    this.pr = pr;
                }

                public Integer getHu() {
                    return hu;
                }

                public void setHu(Integer hu) {
                    this.hu = hu;
                }

                public Double getWs() {
                    return ws;
                }

                public void setWs(Double ws) {
                    this.ws = ws;
                }

                public Integer getWd() {
                    return wd;
                }

                public void setWd(Integer wd) {
                    this.wd = wd;
                }

                public String getIc() {
                    return ic;
                }

                public void setIc(String ic) {
                    this.ic = ic;
                }

            }

            public static class Pollution {

                @SerializedName("ts")
                @Expose
                private String ts;
                @SerializedName("aqius")
                @Expose
                private Integer aqius;
                @SerializedName("mainus")
                @Expose
                private String mainus;
                @SerializedName("aqicn")
                @Expose
                private Integer aqicn;
                @SerializedName("maincn")
                @Expose
                private String maincn;

                public String getTs() {
                    return ts;
                }

                public void setTs(String ts) {
                    this.ts = ts;
                }

                public Integer getAqius() {
                    return aqius;
                }

                public void setAqius(Integer aqius) {
                    this.aqius = aqius;
                }

                public String getMainus() {
                    return mainus;
                }

                public void setMainus(String mainus) {
                    this.mainus = mainus;
                }

                public Integer getAqicn() {
                    return aqicn;
                }

                public void setAqicn(Integer aqicn) {
                    this.aqicn = aqicn;
                }

                public String getMaincn() {
                    return maincn;
                }

                public void setMaincn(String maincn) {
                    this.maincn = maincn;
                }

            }

        }

        public static class Location {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("coordinates")
            @Expose
            private List<Double> coordinates = null;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Double> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }

        }


    }

}
