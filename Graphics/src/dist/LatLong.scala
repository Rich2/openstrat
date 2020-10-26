/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

/** A value of latitude and longitude stored for the earth, stored in arc seconds. The constructor is private as instances will rarely be constructed
 * from arc second values. "ll" and "LL" will be used as an abbreviation for LatLong in method names.  */
final class LatLong private(val latSecs: Double, val longSecs: Double) extends LatLongBase with ProdDbl2
{
  override def toString: String = LatLong.persistImplict.show(this, 0)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[LatLong]
  def _1 = latSecs
  def _2 = longSecs

  def persistName = "LatLong"
  def persistMems = Seq(latRadians, longRadians)  
  def polarRadius: Dist = EarthPolarRadius
  def equatorialRadius: Dist = EarthEquatorialRadius

  /** This method current does not take account of lines that cross the date line, including the Poles */
  def segsTo(num: Int, toPt: LatLong): Seq[LatLong] =
  { val latDelta = toPt.latRadians - latRadians
    val longDelta = toPt.longRadians - longRadians
    Seq(this) ++ (1 to num).map(i => LatLong.radians(latRadians + i * latDelta, longRadians + i * longDelta))
  }

  /** Moves the value northward from this LatLong. This may involve crossing the North Pole or South Pole if the operand is a negative value. When
   *  moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  def addLatRadians(radians: Double): LatLong = Angle.resetRadians(latRadians + radians) match
  { //Going over the north Pole
    case a if a > PiH => LatLong.radians(Pi - a, -longRadians)
    //Going over the south Pole from western longitude
    case a if a < -PiH => LatLong.radians(-Pi - a, -longRadians)
    case a => LatLong.radians(a, longRadians)
  }

  /** When moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  def subLatRadians(radians: Double): LatLong = addLatRadians(-radians)

  /** When moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  def addLongRadians(radians: Double): LatLong = LatLong.radians(latRadians, Angle.resetRadians(longRadians + radians))

  /** When moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  def subLongRadians(radians: Double): LatLong = addLongRadians(-radians)
  
  def addLatSecs(secs: Double): LatLong = Angle.resetSecs(latSecs + secs) match
  { //Going over the north Pole
    case a if a > secsIn90Degs => LatLong.degSecs(secsIn180Degs - a, -longSecs)
    //Going over the south Pole
    case a if a < -secsIn90Degs => LatLong.degSecs(-secsIn180Degs - a, - longSecs)
    case a => LatLong.degSecs(a, longSecs)
  }

  /** Get the XY point from a focus with latitude 0 */
  def xyLat0: Vec2 = Vec2(sin(longRadians) * cos(latRadians), sin(latRadians))

  /** Note this method does not check which side of the earth relative to viewer the polygon verts are */
  def polyToDist2s(inp: LatLongs): Dist2s = inp.pMap(fromFocusDist2)

  def polyToGlobedArea(inp: LatLongs): GlobedArea =
  { val d3s: Dist3s = inp.pMap(el => fromFocusDist3(el))
    d3s.earthZPositive
  }
  def latLongFacing(ll: LatLong): Boolean = fromFocusDist3(ll).z.pos

  def fromFocusDist3(ll: LatLong): Dist3 = ll.subLongRadians(longRadians).toDist3.xRotation(-latRadians)
  def fromFocusLineDist3(inp: LLLineSeg): LineDist3 = LineDist3(
    inp.llStart.subLongRadians(longRadians).toDist3.xRotation(-latRadians),
    inp.latLong2.subLongRadians(longRadians).toDist3.xRotation(-latRadians))

  def fromFocusDist2(ll: LatLong): Dist2 = fromFocusDist3(ll).xy
  def optFromFocusDist2(ll: LatLong): Option[Dist2] =
  { val v3 = fromFocusDist3(ll)
    v3.z.pos.toOption(v3.xy)
  }

  def toOptDist2(inp: LatLong): Option[Dist2] =
  { val r1: Dist3 = inp.subLongRadians(longRadians).toDist3.xRotation(-latRadians)
    r1.toXYIfZPositive
  }
   
  def toVec3(polarRadius: Double, equatorialRadius: Double): Vec3 =
  { val clat = cos(latRadians).abs
    Vec3(sin(longRadians) * equatorialRadius * clat, cos(latRadians) * polarRadius, cos(longRadians) * equatorialRadius * clat)
  }
}

/** Companion object for the [[LatLong]] class. Contains factory methods for the creation of LatLong s.  */
object LatLong
{
  def apply(lat: Latitude, long: Longitude): LatLong = new LatLong(lat.secs, long.secs)

  @inline def radians(latRadians: Double, longRadians: Double): LatLong = //degSecs(latRadians.radiansToSecs, longRadians.radiansToSecs)
  { val lat = ((latRadians + Pi / 2) %% Pi) - Pi / 2
    val long = ((longRadians + Pi) %% Pi2) - Pi
    LatLong.degSecs(lat.radiansToSecs, long.radiansToSecs)
  }

  def degSecs(lat: Double, long: Double): LatLong = new LatLong(lat, long)

  implicit val persistImplict: PersistEq[LatLong] =
    new PersistD2[LatLong]("LatLong", "lat", _.latRadians, "long", _.longRadians, this.radians)

   def degs(lat: Double, long: Double): LatLong = LatLong.degSecs(lat.degsToSecs, long.degsToSecs)
}