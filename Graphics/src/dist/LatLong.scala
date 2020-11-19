/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A value of latitude and longitude stored for the earth, stored in arc seconds. The constructor is private as instances will rarely be constructed
 * from arc second values. "ll" and "LL" will be used as an abbreviation for LatLong in method names.  */
final class LatLong private(val latMilliSecs: Double, val longMilliSecs: Double) extends LatLongBase with ProdDbl2
{
  override def toString: String = LatLong.persistImplict.show(this, 0)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[LatLong]
  def _1 = latSecs
  def _2 = longSecs
  def latSecs: Double = latMilliSecs / 1000
  def longSecs: Double = longMilliSecs / 1000

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
  def addLatRadians(radians: Double): LatLong = (latRadians + radians) %+- Pi1 match
  { //Going over the north Pole
    case a if a > PiOn2 => LatLong.radians(Pi1 - a, -longRadians)
    //Going over the south Pole from western longitude
    case a if a < -PiOn2 => LatLong.radians(-Pi1 - a, -longRadians)
    case a => LatLong.radians(a, longRadians)
  }

  /** Moves the value northward from this LatLong. This may involve crossing the North Pole or South Pole if the operand is a negative value. When
   *  moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  /*def addLat(delta: AngleVec): LatLong = Angle.resetRadians(latRadians + radians) match
  { //Going over the north Pole
    case a if a > PiOn2 => LatLong.radians(Pi1 - a, -longRadians)
    //Going over the south Pole from western longitude
    case a if a < -PiOn2 => LatLong.radians(-Pi1 - a, -longRadians)
    case a => LatLong.radians(a, longRadians)
  }*/

  /** When moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  def subLatRadians(radians: Double): LatLong = addLatRadians(-radians)

  /** When moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  def addLongRadians(radians: Double): LatLong = LatLong.radians(latRadians, (longRadians + radians) %+- Pi1)

  /** When moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  def subLongRadians(radians: Double): LatLong = addLongRadians(-radians)
  
  def addLatSecs(secs: Double): LatLong = (latSecs + secs) %+- SecsIn180Degs match
  { //Going over the north Pole
    case a if a > SecsIn90Degs => LatLong.secs(SecsIn180Degs - a, -longSecs)
    //Going over the south Pole
    case a if a < -SecsIn90Degs => LatLong.secs(-SecsIn180Degs - a, - longSecs)
    case a => LatLong.secs(a, longSecs)
  }

  /** Get the XY point from a focus with latitude 0 */
  def xyLat0: Pt2 = Pt2(longRadians.sin * latRadians.sin, latRadians.sin)

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

  def toVec3(polarRadius: Double, equatorialRadius: Double): Pt3 =
  { val clat = latRadians.cos.abs
    Pt3(longRadians.sin * equatorialRadius * clat, latRadians.cos * polarRadius, longRadians.cos * equatorialRadius * clat)
  }
}

/** Companion object for the [[LatLong]] class. Contains factory methods for the creation of LatLong s.  */
object LatLong
{
  /** Factory method for LatLong, creates LatLong from a [[Latitude]] and a [[Longitude]]. */
  def apply(lat: Latitude, long: Longitude): LatLong = new LatLong(lat.milliSecs, long.milliSecs)

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in degrees, where southern and western
   * values are negative. */
  def degs(lat: Double, long: Double): LatLong = LatLong.milliSecs(lat.degsToMilliSecs, long.degsToMilliSecs)

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in radians, where southern and western
   * values are negative. */
  @inline def radians(latRadians: Double, longRadians: Double): LatLong =
  { val lat = ((latRadians + PiOn2) %% Pi1) - PiOn2
    val long = ((longRadians + Pi1) %% Pi2) - Pi1
    LatLong.secs(lat.radiansToSecs, long.radiansToSecs)
  }

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in arc seconds of a degree, where
   *  southern and western values are negative. */
  def secs(lat: Double, long: Double): LatLong = new LatLong(lat * 1000, long * 1000)

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in thousands of an arc second of a
   *  degree, where southern and western values are negative. */
  def milliSecs(lat: Double, long: Double): LatLong = new LatLong(lat, long)

  implicit val persistImplict: PersistEq[LatLong] =
    new PersistD2[LatLong]("LatLong", "lat", _.latRadians, "long", _.longRadians, this.radians)
}