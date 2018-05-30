/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pEarth

case class EarthView (latLong: LatLong, scale: Dist, up: Boolean)

object EarthView
{
   /** Not sure about the scale .metres parameter conversion */
   implicit object EarthViewPersist extends Persist3[LatLong, Double, Boolean, EarthView]('EarthView,
         ev => (ev.latLong, ev.scale.metres / 1000, ev.up),
         (ll: LatLong, d: Double, b: Boolean) => EarthView(ll, Dist(d * 1000), b))   
}