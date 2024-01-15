package models;

/**
 * The class represents a model for weather forecast information.
 *
 * It encapsulates various attributes related to weather conditions, including location details,
 * temperature, atmospheric pressure, humidity, wind speed, visibility, and additional information
 * such as rain and snow.
 *
 * This class follows the Builder design pattern for convenient and readable instantiation.
 * Example usage:
 * WeatherForecast weather = WeatherForecast.newBuilder()
 *                                       .setCity("New York")
 *                                       .setLatitude(40.7128)
 *                                       .setLongitude(-74.0060)
 *                                       // ... set other parameters ...
 *                                       .build();
 *
 * Note: The class includes a static inner Builder class for constructing instances with a fluent interface.
 */
public class WeatherForecast {
    private String city;
    private double longitude;
    private double latitude;
    private String weatherParam;
    private String additionWeatherParams;
    private String temp;
    private String tempFeelsLike;
    private double atmPressure;             //hPa
    private double humidity;                //%
    private double visibility;              //km
    private double windSpeed;               //m/s
    private String windDir;
    private String rain;
    private String snow;
    private String sunriseTime;             //HH:mm:ss
    private String sunsetTime;              //HH:mm:ss
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getWeatherParam() {
        return weatherParam;
    }
    public void setWeatherParam(String weatherParam) {
        this.weatherParam = weatherParam;
    }
    public String getAdditionWeatherParams() {
        return additionWeatherParams;
    }
    public void setAdditionWeatherParams(String additionWeatherParams) {
        this.additionWeatherParams = additionWeatherParams;
    }
    public String getTemp() {
        return temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public String getTempFeelsLike() {
        return tempFeelsLike;
    }
    public void setTempFeels_like(String tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }
    public double getAtmPressure() {
        return atmPressure;
    }
    public void setAtmPressure(double atmPressure) {
        this.atmPressure = atmPressure;
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    public double getVisibility() {
        return visibility;
    }
    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public String getWindDir() {
        return windDir;
    }
    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }
    public String getRain() {
        return rain;
    }
    public void setRain(String rain) {
        this.rain = rain;
    }
    public String getSnow() {
        return snow;
    }
    public void setSnow(String snow) {
        this.snow = snow;
    }
    public String getSunriseTime() {
        return sunriseTime;
    }
    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }
    public String getSunsetTime() {
        return sunsetTime;
    }
    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }
    public static Builder newBuilder() {
        return new Builder();
    }
    public static class Builder {
        private WeatherForecast weatherForecast = new WeatherForecast();
        public Builder setCity(String city) {
            weatherForecast.city = city;
            return this;
        }
        public Builder setLongitude(double longitude) {
            weatherForecast.longitude = longitude;
            return this;
        }
        public Builder setLatitude(double latitude) {
            weatherForecast.latitude = latitude;
            return this;
        }
        public Builder setWeatherParam(String weatherParam) {
            weatherForecast.weatherParam = weatherParam;
            return this;
        }
        public Builder setAdditionWeatherParams(String additionWeatherParams) {
            weatherForecast.additionWeatherParams = additionWeatherParams;
            return this;
        }
        public Builder setTemp(String temp) {
            weatherForecast.temp = temp;
            return this;
        }
        public Builder setTempFeelsLike(String tempFeelsLike) {
            weatherForecast.tempFeelsLike = tempFeelsLike;
            return this;
        }
        public Builder setAtmPressure(double atmPressure) {
            weatherForecast.atmPressure = atmPressure;
            return this;
        }
        public Builder setHumidity(double humidity) {
            weatherForecast.humidity = humidity;
            return this;
        }
        public Builder setVisibility(double visibility) {
            weatherForecast.visibility = visibility;
            return this;
        }
        public Builder setWindSpeed(double windSpeed) {
            weatherForecast.windSpeed = windSpeed;
            return this;
        }
        public Builder setWindDir(String windDir) {
            weatherForecast.windDir = windDir;
            return this;
        }
        public Builder setRain(String rain) {
            weatherForecast.rain = rain;
            return this;
        }
        public Builder setSnow(String snow) {
            weatherForecast.snow = snow;
            return this;
        }
        public Builder setSunriseTime(String sunriseTime) {
            weatherForecast.sunriseTime = sunriseTime;
            return this;
        }
        public Builder setSunsetTime(String sunsetTime) {
            weatherForecast.sunsetTime = sunsetTime;
            return this;
        }
        public WeatherForecast build() {
            return weatherForecast;
        }
    }
}