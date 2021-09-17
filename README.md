# WeatherWidget

## Description

Widget android to display the weather, more precisely: the state of the sky, the wind speed and its direction (where it comes from). 

Are displayed, a **history** of the last 6/12 hours, the **current weather**, and a **forecast** of the future 6/12 hours. A colored line gives an idea of the weather condition : Green <-> Red shades. A click change the state from 6hours view to 12hours view (1 or 2h gap between each line)

## TODO

I want to add some improvements to this widget :

1) Doing the application itself, for now, is a basic empty activity. I want to use it to configure some aspects of the widget: location, time preference, maybe more...
2) Add a second mode, with one click the forecast changes from a 1h delay to 2h to give a better longer term forecast (so the last forecast changes from 9h in the future to 18h). Another click turn back to the default mode with 1h delay.

## Use It

Add a resource file in the app/res/values folder with the id "api_key" that contains your OpenWeather apik key, and you're done!
(Maybe the layout won't be right for your phone, I did it for my One Plus 6T, so you may have to change some width/height values, but not a big deal I think)

## Démo :

5 x 1
![](https://github.com/TonyChouteau/WeatherWindWidget/blob/main/images/v3/5x1.jpg)
5 x 2
![](https://github.com/TonyChouteau/WeatherWindWidget/blob/main/images/v3/5x2.jpg)
5 x 3
![](https://github.com/TonyChouteau/WeatherWindWidget/blob/main/images/v3/5x3.jpg)
