
package com.example.weatherforecastapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WeatherModel implements Serializable {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("currently")
    @Expose
    private Currently currently;
    @SerializedName("hourly")
    @Expose
    private Hourly hourly;
    @SerializedName("daily")
    @Expose
    private Daily daily;
    @SerializedName("flags")
    @Expose
    private Flags flags;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }


    public static class Currently {

        @SerializedName("time")
        @Expose
        private Integer time;
        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("precipIntensity")
        @Expose
        private Double precipIntensity;
        @SerializedName("precipProbability")
        @Expose
        private Double precipProbability;
        @SerializedName("precipType")
        @Expose
        private String precipType;
        @SerializedName("temperature")
        @Expose
        private Double temperature;
        @SerializedName("apparentTemperature")
        @Expose
        private Double apparentTemperature;
        @SerializedName("dewPoint")
        @Expose
        private Double dewPoint;
        @SerializedName("humidity")
        @Expose
        private Double humidity;
        @SerializedName("pressure")
        @Expose
        private Double pressure;
        @SerializedName("windSpeed")
        @Expose
        private Double windSpeed;
        @SerializedName("windGust")
        @Expose
        private Double windGust;
        @SerializedName("windBearing")
        @Expose
        private Double windBearing;
        @SerializedName("cloudCover")
        @Expose
        private Double cloudCover;
        @SerializedName("uvIndex")
        @Expose
        private Integer uvIndex;
        @SerializedName("visibility")
        @Expose
        private Double visibility;
        @SerializedName("ozone")
        @Expose
        private Double ozone;

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Double getPrecipIntensity() {
            return precipIntensity;
        }

        public void setPrecipIntensity(Double precipIntensity) {
            this.precipIntensity = precipIntensity;
        }

        public Double getPrecipProbability() {
            return precipProbability;
        }

        public void setPrecipProbability(Double precipProbability) {
            this.precipProbability = precipProbability;
        }

        public String getPrecipType() {
            return precipType;
        }

        public void setPrecipType(String precipType) {
            this.precipType = precipType;
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public Double getApparentTemperature() {
            return apparentTemperature;
        }

        public void setApparentTemperature(Double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
        }

        public Double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(Double dewPoint) {
            this.dewPoint = dewPoint;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(Double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public Double getWindGust() {
            return windGust;
        }

        public void setWindGust(Double windGust) {
            this.windGust = windGust;
        }

        public Double getWindBearing() {
            return windBearing;
        }

        public void setWindBearing(Double windBearing) {
            this.windBearing = windBearing;
        }

        public Double getCloudCover() {
            return cloudCover;
        }

        public void setCloudCover(Double cloudCover) {
            this.cloudCover = cloudCover;
        }

        public Integer getUvIndex() {
            return uvIndex;
        }

        public void setUvIndex(Integer uvIndex) {
            this.uvIndex = uvIndex;
        }

        public Double getVisibility() {
            return visibility;
        }

        public void setVisibility(Double visibility) {
            this.visibility = visibility;
        }

        public Double getOzone() {
            return ozone;
        }

        public void setOzone(Double ozone) {
            this.ozone = ozone;
        }

    }


    public static class Flags {

        @SerializedName("sources")
        @Expose
        private List<String> sources = null;
        @SerializedName("nearest-station")
        @Expose
        private Double nearestStation;
        @SerializedName("units")
        @Expose
        private String units;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public Double getNearestStation() {
            return nearestStation;
        }

        public void setNearestStation(Double nearestStation) {
            this.nearestStation = nearestStation;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }

    }


    public static class Hourly implements Serializable{

        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public static class Datum implements Serializable{

            @SerializedName("time")
            @Expose
            private Integer time;
            @SerializedName("summary")
            @Expose
            private String summary;
            @SerializedName("icon")
            @Expose
            private String icon;
            @SerializedName("precipIntensity")
            @Expose
            private Double precipIntensity;
            @SerializedName("precipProbability")
            @Expose
            private Double precipProbability;
            @SerializedName("precipType")
            @Expose
            private String precipType;
            @SerializedName("temperature")
            @Expose
            private Double temperature;
            @SerializedName("apparentTemperature")
            @Expose
            private Double apparentTemperature;
            @SerializedName("dewPoint")
            @Expose
            private Double dewPoint;
            @SerializedName("humidity")
            @Expose
            private Double humidity;
            @SerializedName("pressure")
            @Expose
            private Double pressure;
            @SerializedName("windSpeed")
            @Expose
            private Double windSpeed;
            @SerializedName("windGust")
            @Expose
            private Double windGust;
            @SerializedName("windBearing")
            @Expose
            private Double windBearing;
            @SerializedName("cloudCover")
            @Expose
            private Double cloudCover;
            @SerializedName("uvIndex")
            @Expose
            private Integer uvIndex;
            @SerializedName("visibility")
            @Expose
            private Double visibility;
            @SerializedName("ozone")
            @Expose
            private Double ozone;

            public Integer getTime() {
                return time;
            }

            public void setTime(Integer time) {
                this.time = time;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public Double getPrecipIntensity() {
                return precipIntensity;
            }

            public void setPrecipIntensity(Double precipIntensity) {
                this.precipIntensity = precipIntensity;
            }

            public Double getPrecipProbability() {
                return precipProbability;
            }

            public void setPrecipProbability(Double precipProbability) {
                this.precipProbability = precipProbability;
            }

            public String getPrecipType() {
                return precipType;
            }

            public void setPrecipType(String precipType) {
                this.precipType = precipType;
            }

            public Double getTemperature() {
                return temperature;
            }

            public void setTemperature(Double temperature) {
                this.temperature = temperature;
            }

            public Double getApparentTemperature() {
                return apparentTemperature;
            }

            public void setApparentTemperature(Double apparentTemperature) {
                this.apparentTemperature = apparentTemperature;
            }

            public Double getDewPoint() {
                return dewPoint;
            }

            public void setDewPoint(Double dewPoint) {
                this.dewPoint = dewPoint;
            }

            public Double getHumidity() {
                return humidity;
            }

            public void setHumidity(Double humidity) {
                this.humidity = humidity;
            }

            public Double getPressure() {
                return pressure;
            }

            public void setPressure(Double pressure) {
                this.pressure = pressure;
            }

            public Double getWindSpeed() {
                return windSpeed;
            }

            public void setWindSpeed(Double windSpeed) {
                this.windSpeed = windSpeed;
            }

            public Double getWindGust() {
                return windGust;
            }

            public void setWindGust(Double windGust) {
                this.windGust = windGust;
            }

            public Double getWindBearing() {
                return windBearing;
            }

            public void setWindBearing(Double windBearing) {
                this.windBearing = windBearing;
            }

            public Double getCloudCover() {
                return cloudCover;
            }

            public void setCloudCover(Double cloudCover) {
                this.cloudCover = cloudCover;
            }

            public Integer getUvIndex() {
                return uvIndex;
            }

            public void setUvIndex(Integer uvIndex) {
                this.uvIndex = uvIndex;
            }

            public Double getVisibility() {
                return visibility;
            }

            public void setVisibility(Double visibility) {
                this.visibility = visibility;
            }

            public Double getOzone() {
                return ozone;
            }

            public void setOzone(Double ozone) {
                this.ozone = ozone;
            }

        }

    }


    public static class Daily {

        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("data")
        @Expose
        private List<Datum_> data = null;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<Datum_> getData() {
            return data;
        }

        public void setData(List<Datum_> data) {
            this.data = data;
        }

        public static class Datum_ implements Serializable {

            @SerializedName("time")
            @Expose
            private Integer time;
            @SerializedName("summary")
            @Expose
            private String summary;
            @SerializedName("icon")
            @Expose
            private String icon;
            @SerializedName("sunriseTime")
            @Expose
            private Integer sunriseTime;
            @SerializedName("sunsetTime")
            @Expose
            private Integer sunsetTime;
            @SerializedName("moonPhase")
            @Expose
            private Double moonPhase;
            @SerializedName("precipIntensity")
            @Expose
            private Double precipIntensity;
            @SerializedName("precipIntensityMax")
            @Expose
            private Double precipIntensityMax;
            @SerializedName("precipIntensityMaxTime")
            @Expose
            private Integer precipIntensityMaxTime;
            @SerializedName("precipProbability")
            @Expose
            private Double precipProbability;
            @SerializedName("precipType")
            @Expose
            private String precipType;
            @SerializedName("temperatureHigh")
            @Expose
            private Double temperatureHigh;
            @SerializedName("temperatureHighTime")
            @Expose
            private Integer temperatureHighTime;
            @SerializedName("temperatureLow")
            @Expose
            private Double temperatureLow;
            @SerializedName("temperatureLowTime")
            @Expose
            private Integer temperatureLowTime;
            @SerializedName("apparentTemperatureHigh")
            @Expose
            private Double apparentTemperatureHigh;
            @SerializedName("apparentTemperatureHighTime")
            @Expose
            private Integer apparentTemperatureHighTime;
            @SerializedName("apparentTemperatureLow")
            @Expose
            private Double apparentTemperatureLow;
            @SerializedName("apparentTemperatureLowTime")
            @Expose
            private Integer apparentTemperatureLowTime;
            @SerializedName("dewPoint")
            @Expose
            private Double dewPoint;
            @SerializedName("humidity")
            @Expose
            private Double humidity;
            @SerializedName("pressure")
            @Expose
            private Double pressure;
            @SerializedName("windSpeed")
            @Expose
            private Double windSpeed;
            @SerializedName("windGust")
            @Expose
            private Double windGust;
            @SerializedName("windGustTime")
            @Expose
            private Integer windGustTime;
            @SerializedName("windBearing")
            @Expose
            private Double windBearing;
            @SerializedName("cloudCover")
            @Expose
            private Double cloudCover;
            @SerializedName("uvIndex")
            @Expose
            private Integer uvIndex;
            @SerializedName("uvIndexTime")
            @Expose
            private Integer uvIndexTime;
            @SerializedName("visibility")
            @Expose
            private Double visibility;
            @SerializedName("ozone")
            @Expose
            private Double ozone;
            @SerializedName("temperatureMin")
            @Expose
            private Double temperatureMin;
            @SerializedName("temperatureMinTime")
            @Expose
            private Integer temperatureMinTime;
            @SerializedName("temperatureMax")
            @Expose
            private Double temperatureMax;
            @SerializedName("temperatureMaxTime")
            @Expose
            private Integer temperatureMaxTime;
            @SerializedName("apparentTemperatureMin")
            @Expose
            private Double apparentTemperatureMin;
            @SerializedName("apparentTemperatureMinTime")
            @Expose
            private Integer apparentTemperatureMinTime;
            @SerializedName("apparentTemperatureMax")
            @Expose
            private Double apparentTemperatureMax;
            @SerializedName("apparentTemperatureMaxTime")
            @Expose
            private Integer apparentTemperatureMaxTime;

            public Integer getTime() {
                return time;
            }

            public void setTime(Integer time) {
                this.time = time;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public Integer getSunriseTime() {
                return sunriseTime;
            }

            public void setSunriseTime(Integer sunriseTime) {
                this.sunriseTime = sunriseTime;
            }

            public Integer getSunsetTime() {
                return sunsetTime;
            }

            public void setSunsetTime(Integer sunsetTime) {
                this.sunsetTime = sunsetTime;
            }

            public Double getMoonPhase() {
                return moonPhase;
            }

            public void setMoonPhase(Double moonPhase) {
                this.moonPhase = moonPhase;
            }

            public Double getPrecipIntensity() {
                return precipIntensity;
            }

            public void setPrecipIntensity(Double precipIntensity) {
                this.precipIntensity = precipIntensity;
            }

            public Double getPrecipIntensityMax() {
                return precipIntensityMax;
            }

            public void setPrecipIntensityMax(Double precipIntensityMax) {
                this.precipIntensityMax = precipIntensityMax;
            }

            public Integer getPrecipIntensityMaxTime() {
                return precipIntensityMaxTime;
            }

            public void setPrecipIntensityMaxTime(Integer precipIntensityMaxTime) {
                this.precipIntensityMaxTime = precipIntensityMaxTime;
            }

            public Double getPrecipProbability() {
                return precipProbability;
            }

            public void setPrecipProbability(Double precipProbability) {
                this.precipProbability = precipProbability;
            }

            public String getPrecipType() {
                return precipType;
            }

            public void setPrecipType(String precipType) {
                this.precipType = precipType;
            }

            public Double getTemperatureHigh() {
                return temperatureHigh;
            }

            public void setTemperatureHigh(Double temperatureHigh) {
                this.temperatureHigh = temperatureHigh;
            }

            public Integer getTemperatureHighTime() {
                return temperatureHighTime;
            }

            public void setTemperatureHighTime(Integer temperatureHighTime) {
                this.temperatureHighTime = temperatureHighTime;
            }

            public Double getTemperatureLow() {
                return temperatureLow;
            }

            public void setTemperatureLow(Double temperatureLow) {
                this.temperatureLow = temperatureLow;
            }

            public Integer getTemperatureLowTime() {
                return temperatureLowTime;
            }

            public void setTemperatureLowTime(Integer temperatureLowTime) {
                this.temperatureLowTime = temperatureLowTime;
            }

            public Double getApparentTemperatureHigh() {
                return apparentTemperatureHigh;
            }

            public void setApparentTemperatureHigh(Double apparentTemperatureHigh) {
                this.apparentTemperatureHigh = apparentTemperatureHigh;
            }

            public Integer getApparentTemperatureHighTime() {
                return apparentTemperatureHighTime;
            }

            public void setApparentTemperatureHighTime(Integer apparentTemperatureHighTime) {
                this.apparentTemperatureHighTime = apparentTemperatureHighTime;
            }

            public Double getApparentTemperatureLow() {
                return apparentTemperatureLow;
            }

            public void setApparentTemperatureLow(Double apparentTemperatureLow) {
                this.apparentTemperatureLow = apparentTemperatureLow;
            }

            public Integer getApparentTemperatureLowTime() {
                return apparentTemperatureLowTime;
            }

            public void setApparentTemperatureLowTime(Integer apparentTemperatureLowTime) {
                this.apparentTemperatureLowTime = apparentTemperatureLowTime;
            }

            public Double getDewPoint() {
                return dewPoint;
            }

            public void setDewPoint(Double dewPoint) {
                this.dewPoint = dewPoint;
            }

            public Double getHumidity() {
                return humidity;
            }

            public void setHumidity(Double humidity) {
                this.humidity = humidity;
            }

            public Double getPressure() {
                return pressure;
            }

            public void setPressure(Double pressure) {
                this.pressure = pressure;
            }

            public Double getWindSpeed() {
                return windSpeed;
            }

            public void setWindSpeed(Double windSpeed) {
                this.windSpeed = windSpeed;
            }

            public Double getWindGust() {
                return windGust;
            }

            public void setWindGust(Double windGust) {
                this.windGust = windGust;
            }

            public Integer getWindGustTime() {
                return windGustTime;
            }

            public void setWindGustTime(Integer windGustTime) {
                this.windGustTime = windGustTime;
            }

            public Double getWindBearing() {
                return windBearing;
            }

            public void setWindBearing(Double windBearing) {
                this.windBearing = windBearing;
            }

            public Double getCloudCover() {
                return cloudCover;
            }

            public void setCloudCover(Double cloudCover) {
                this.cloudCover = cloudCover;
            }

            public Integer getUvIndex() {
                return uvIndex;
            }

            public void setUvIndex(Integer uvIndex) {
                this.uvIndex = uvIndex;
            }

            public Integer getUvIndexTime() {
                return uvIndexTime;
            }

            public void setUvIndexTime(Integer uvIndexTime) {
                this.uvIndexTime = uvIndexTime;
            }

            public Double getVisibility() {
                return visibility;
            }

            public void setVisibility(Double visibility) {
                this.visibility = visibility;
            }

            public Double getOzone() {
                return ozone;
            }

            public void setOzone(Double ozone) {
                this.ozone = ozone;
            }

            public Double getTemperatureMin() {
                return temperatureMin;
            }

            public void setTemperatureMin(Double temperatureMin) {
                this.temperatureMin = temperatureMin;
            }

            public Integer getTemperatureMinTime() {
                return temperatureMinTime;
            }

            public void setTemperatureMinTime(Integer temperatureMinTime) {
                this.temperatureMinTime = temperatureMinTime;
            }

            public Double getTemperatureMax() {
                return temperatureMax;
            }

            public void setTemperatureMax(Double temperatureMax) {
                this.temperatureMax = temperatureMax;
            }

            public Integer getTemperatureMaxTime() {
                return temperatureMaxTime;
            }

            public void setTemperatureMaxTime(Integer temperatureMaxTime) {
                this.temperatureMaxTime = temperatureMaxTime;
            }

            public Double getApparentTemperatureMin() {
                return apparentTemperatureMin;
            }

            public void setApparentTemperatureMin(Double apparentTemperatureMin) {
                this.apparentTemperatureMin = apparentTemperatureMin;
            }

            public Integer getApparentTemperatureMinTime() {
                return apparentTemperatureMinTime;
            }

            public void setApparentTemperatureMinTime(Integer apparentTemperatureMinTime) {
                this.apparentTemperatureMinTime = apparentTemperatureMinTime;
            }

            public Double getApparentTemperatureMax() {
                return apparentTemperatureMax;
            }

            public void setApparentTemperatureMax(Double apparentTemperatureMax) {
                this.apparentTemperatureMax = apparentTemperatureMax;
            }

            public Integer getApparentTemperatureMaxTime() {
                return apparentTemperatureMaxTime;
            }

            public void setApparentTemperatureMaxTime(Integer apparentTemperatureMaxTime) {
                this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
            }

        }

    }

}