/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
package pPts
import geom._
import Terrain._

object Polandia extends Area2('Polandia, 50.07 ll 20.13, plain)
{
   val mielno = 54.26 ll 16.06
   val jaroslawiec = 54.54 ll 16.53
   val jastrzebia = 54.83 ll 18.33
   val wladyslawowo = 54.79 ll 18.42
   val danzig = 54.39 ll 18.65
   val kaliningrad = 54.93 ll 21.26
   val cenEast = 52 ll 24
   
   val latLongs = LatLongs(Germania.swinoujscie, mielno, jaroslawiec, jastrzebia, wladyslawowo, danzig, kaliningrad, cenEast, Balkans.odessa,
         Alpsland.zagreb, Alpsland.vienna)
}

object Finlandia extends Area2('Scandanavia, 65.56 ll 29.95, taiga)
{
   val lisyNos = 60.01 ll 29.96
   val laskovyy = 60.15 ll 29.92
   val ozerki = 60.18 ll 29.01
   val baltiyets = 60.61 ll 28.38
   val helsinki = 60.15 ll 24.94
   val hanko = 59.82 ll 22.94
   val point1 = 59.92 ll 22.89
   val kimitoonSE = 60.01 ll 22.76
   val hyppeis = 60.22 ll 21.26
   val lyperto = 60.61 ll 21.16
   val pooskeri = 61.80 ll 21.43
   val sidebySW = 61.99 ll 21.28
   val wVaasa = 63.11 ll 21.47
   val oulu = 65.00 ll 25.41
   val olhava = 65.46 ll 25.33
   
   val svaerholt = 70.96 ll 26.67
   val vardo = 70.36 ll 31.12
   val karlebotn = 70.11 ll 28.57
   val tulomaMouth = 69.33 ll 33.56   
   val wMurmanask = 67.11 ll 41.28
   val sMurmansk = 66.07 ll 38.44   
   val kandalasaksha = 67.13 ll 32.26
   
   val pusunsaari = 61.55 ll 31.43
   val ladogaNorth = 61.61 ll 30.92
   val ladogaNW = 61.17 ll 29.98
   val ladozhskiy = 60.02 ll 31.12
   
   val latLongs = LatLongs(Baltland.piterland, lisyNos, laskovyy, ozerki, baltiyets, helsinki, hanko, point1, kimitoonSE, hyppeis, lyperto,
         pooskeri, sidebySW, wVaasa, oulu, olhava, Scandanavia.haparanda, Scandanavia.lakselv, svaerholt, vardo, karlebotn, tulomaMouth,
         wMurmanask, sMurmansk, kandalasaksha,
         Baltland.onezhsky, Baltland.ladogaEast, pusunsaari, ladogaNorth, ladogaNW, ladozhskiy, Baltland.nevaMouth)
}

object Gotland extends Area2('Gotland, 57.46 ll 18.47, plain)
{
   val southWest = 56.90 ll 18.12
   val west = 57.26 ll 18.09
   val tofta = 57.53 ll 18.10
   val hallshuk = 57.92 ll 18.73
   val east = 57.96 ll 19.35
   
   val latLongs = LatLongs(southWest, west, tofta, hallshuk, east)   
}

object Baltland extends Area2('BaltLand, 56.46 ll 27.83, plain)
{
   val klaipeda = 55.73 ll 21.08
   val ziemupe = 56.83 ll 21.06
   val ovsi = 57.57 ll 21.71
   val kolka = 57.75 ll 22.06
   val jurmala = 56.96 ll 23.66
   val saulkrasti = 57.28 ll 24.41
   val parnu = 58.37 ll 24.47
   val lao = 58.23 ll 24.12
   val virtsu = 58.56 ll 23.50
   val noarootsi = 59.2 ll 23.5
   val paldiski = 59.40 ll 24.04
   val udria = 59.40 ll 27.92
   val krasnoselsky = 59.86 ll 30.14
   val piterland = 59.97 ll 30.21
   val nevaMouth = 59.95 ll 31.04
   val ladogaEast = 60.66 ll 32.96
   val onezhsky = 63.79 ll 37.35
   val north = 66.51 ll 42.25
   val mezenMouth = 66.07 ll 44.10
   val southEast = 52 ll 45   
   
   val latLongs = LatLongs(Polandia.kaliningrad, klaipeda, ziemupe, ovsi, kolka, jurmala, saulkrasti, parnu, lao, virtsu,
         noarootsi, paldiski, udria, krasnoselsky, piterland, nevaMouth, ladogaEast,
         onezhsky, north, mezenMouth, southEast, Polandia.cenEast)   
}

object Saaremaa extends Area2('Saaremaa, 58.43 ll 22.52, plain)
{
   val south = 57.91 ll 22.03
   val uudibe = 58.15 ll 22.21
   val west = 58.03 ll 21.82
   val northWest = 58.51 ll 21.91
   val nommkula = 58.68 ll 23.19
   val loetsa = 58.64 ll 23.34
   val east = 58.55 ll 23.40
   val tehumardi = 58.18 ll 22.25
   
   val latLongs = LatLongs(south, uudibe, west, northWest, nommkula, loetsa, east, tehumardi)
}

object Hiiumaa extends Area2('Hiiumaa, 58.90 ll 22.63, plain)
{
   val west = 58.92 ll 22.04
   val north = 59.08 ll 22.65
   val sarve = 58.83 ll 23.05
   val southEast = 58.70 ll 22.67
   val southWest = 58.7 ll 22.49
   val latLongs = LatLongs(west, north, sarve, southEast, southWest)
}
      
      