/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** A view of the Earth. Currently North can only be up or down. */
class EarthView (val latDegs: Double, val longDegs: Double, val scaleKm: Double, val up: Boolean)
{
  def latLong: LatLong = LatLong.degs(latDegs, longDegs)
  def scale: Length = scaleKm * 1.km
}

object EarthView
{
  def apply(latDegs: Double, longDegs: Double, kms: Double, up: Boolean = true): EarthView = new EarthView(latDegs, longDegs, kms, up)
  def apply(latLong: LatLong, scale: Length, up: Boolean): EarthView = new EarthView(latLong.latDegs, latLong.longDegs, scale.kMetresNum, up)

  /** Not sure about the scale .metres parameter conversion */
  implicit val earthViewPersistPersist3: Persist3[LatLong, Double, Boolean, EarthView] =
    Persist3[LatLong, Double, Boolean, EarthView]("EarthView", "latLong", _.latLong,"scale", _.scale.metresNum / 1000, "up", _.up,
    (ll: LatLong, d: Double, b: Boolean) => EarthView(ll, Length(d * 1000), b))
}