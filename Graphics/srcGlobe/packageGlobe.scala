/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom;

package object pglobe
{
  val LatLong0: LatLong = LatLong.degs(0, 0)

  extension (thisInt: Int)
  { /** infix extension method for creating a latitude-longitude value [[LatLong]]. This [[Int]] is the latitude specified in degrees. The operand
     * parameter is the longitude specified in degrees. */
    infix def ll (longDegs: Double): LatLong = LatLong.degs(thisInt, longDegs)

    /** Extension method returns a longitude where this [[Int]] is the value east specified in degrees. An Int -180 > i < 0 returns a western
     *  value. */
    def east: Longitude = Longitude.degs(thisInt)

    /** Extension method returns a longitude where this [[Int]] is the value west specified in degrees. An Int -180 > i < 0 returns an eastern
     *  value. */
    def west: Longitude = Longitude.degs(-thisInt)

    /** Extension method returns a latitude where this [[Int]] is the value north specified in degrees. An Int -180 > i < 0 returns a southern
     *  value. */
    def north: Latitude = Latitude.apply(thisInt)

    /** Extension method returns a latitude where this [[Int]] is the value south specified in degrees. An Int -180 > i < 0 returns a northern
     *  value. */
    def south: Latitude = Latitude.apply(-thisInt)
  }

  extension (thisDouble: Double)
  {
    /** infix extension method for creating a latitude-longitude value [[LatLong]]. This [[Double]] is the latitude specified in degrees. The operand
     * parameter is the longitude specified in degrees. */
    infix def ll (longDegs: Double): LatLong = LatLong.degs(thisDouble, longDegs)

    /** Extension method returns a longitude where this [Double] is the value east specified in degrees. A Double -180 > d < 0 returns a western
     *  value. */
    def east: Longitude = Longitude.degs(thisDouble)

    /** Extension method returns a longitude where this [[Double]] is the value west specified in degrees. A Double -180 > d < 0 returns an eastern
     *  value. */
    def west: Longitude = Longitude.degs(-thisDouble)

    /** Extension method returns a latitude where this [[Double]] is the value north specified in degrees. A Double -180 > d < 0 returns a southern
     *  value. */
    def north: Latitude = Latitude.apply(thisDouble)

    /** Extension method returns a latitude where this [[Double]] is the value south specified in degrees. A Double -180 > d < 0 returns a northern
     *  value. */
    def south: Latitude = Latitude.apply(-thisDouble)
  }
}
