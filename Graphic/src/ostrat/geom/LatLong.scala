/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math._

/** longitude and latitude measured in radians for the earth. "ll" and "LL" will be used as an abbreviation for LatLong in method names.  */
class LatLong (val lat: Double, val long: Double) extends LatLongBase with ProdD2
{
  override def toString: String = LatLong.PersistImplict.show(this)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[LatLong]
  def _1 = lat
  def _2 = long
  def persistName = "LatLong"
  def persistMems = Seq(lat, long)
  def polarRadius: Dist = EarthPolarRadius
  def equatorialRadius: Dist = EarthEquatorialRadius
  def +(other: LatLong): LatLong = addLong(other.long).addLat(other.lat)
  def -(other: LatLong): LatLong = addLong(-other.long).addLat(-other.lat)

  /** This method current does not take account of lines that cross the date line, including the Poles */
  def segsTo(num: Int, toPt: LatLong): Seq[LatLong] =
  { val latDelta = toPt.lat - lat
    val longDelta = toPt.long - long
    Seq(this) ++ (1 to num).map(i => LatLong(lat + i * latDelta, long + i * longDelta))
   }
   
  def addLat(radians: Double): LatLong = Angle.reset(lat + radians) match
  { //Going over the north Pole from western longitude
    case a if a > PiH && long <= 0 => LatLong(Pi - a, long + Pi)
    //Going over the north Pole from an eastern longitude
    case a if a > PiH             =>  LatLong(Pi - a, long - Pi)
    //Going over the south Pole from western longitude
    case a if a < -PiH && long < 0 => LatLong(-Pi - a, Pi + long)
    //Going over the south Pole from eastern longitude
    case a if a < -PiH             => LatLong(-Pi - a, long - Pi)
    case a => LatLong(a, long)
  }
      
  def subLat(radians: Double): LatLong = addLat(-radians)
  def addLong(radians: Double): LatLong = LatLong(lat, Angle.reset(long + radians))
  def subLong(radians: Double): LatLong = addLong(-radians)
   
  /** Get the XY point from a focus with latitude 0 */
  def xyLat0: Vec2 = Vec2(sin(long) * cos(lat), sin(lat))
   
  /** Note this method does not check which side of the earth relative to viewer the polygon verts are */
  def polyToDist2s(inp: LatLongs): Dist2s = inp.pMap(fromFocusDist2)
   
  def polyToGlobedArea(inp: LatLongs): GlobedArea =
  { val d3s: Dist3s = inp.pMap(el => fromFocusDist3(el))
    d3s.earthZPositive
  }
  def latLongFacing(ll: LatLong): Boolean = fromFocusDist3(ll).z.pos
   
  def fromFocusDist3(ll: LatLong): Dist3 = ll.subLong(long).toDist3.xRotation(-lat)
  def fromFocusLineDist3(inp: LatLongLine): LineDist3 = LineDist3(
    inp.llStart.subLong(long).toDist3.xRotation(-lat),
    inp.latLong2.subLong(long).toDist3.xRotation(-lat))
         
  def fromFocusDist2(ll: LatLong): Dist2 = fromFocusDist3(ll).xy
  def optFromFocusDist2(ll: LatLong): Option[Dist2] =
  { val v3 = fromFocusDist3(ll)
    v3.z.pos.toOption(v3.xy)
  }

  def toOptDist2(inp: LatLong): Option[Dist2] =
  { val r1: Dist3 = inp.subLong(long).toDist3.xRotation(-lat)
    r1.toXYIfZPositive
  }
   
  def toVec3(polarRadius: Double, equatorialRadius: Double): Vec3 =
  { val clat = cos(lat).abs
    Vec3(sin(long) * equatorialRadius * clat, cos(lat) * polarRadius, cos(long) * equatorialRadius * clat)
  }
}

object LatLong
{
  @inline def apply(latRadians: Double, longRadians: Double): LatLong =
  { val lat = ((latRadians + Pi / 2) %% Pi) - Pi / 2
    val long = ((longRadians + Pi) %% Pi2) - Pi
    new LatLong(lat, long)
  }
   
  implicit object PersistImplict extends PersistD2[LatLong]("LatLong", ll => (ll.lat, ll.long), apply)
   
  //def apply(latAngle: Latitude, longAngle: Longitude): LatLong = new LatLong(latAngle.radians, longAngle.radians)
   //def ll(lat: Latitude, long: Longitude) = new LatLong(lat.radians, long.radians)
   def deg(lat: Double, long: Double): LatLong = LatLong(lat.degreesToRadians, long.degreesToRadians)
  // def lDeg(lat: Latitude, long: Double): LatLong = LatLong(lat.radians, long.degreesToRadians)
 //  def degL(lat: Double, long: Longitude): LatLong = LatLong(lat * Pi / 180.0, long.radians)
   
//   def neMin(latDegs: Int, latMinutes: Int, longDegs: Int, longMinutes: Int): LatLong =
//   {
//      val lat = ((latMinutes / 60.0) + latDegs) * Pi / 180
//      val long = ((longMinutes / 60.0) + longDegs) * Pi / 180      
//      LatLong(lat, long)
//   }
   //def nwMin(latDegs: Int, latMinutes: Int, longDegs: Int, longMinutes: Int): LatLong = neMin(latDegs, latMinutes, -longDegs, -longMinutes)
   //def seMin(latDegs: Int, latMinutes: Int, longDegs: Int, longMinutes: Int): LatLong = neMin(-latDegs, -latMinutes, longDegs, longMinutes)
   //def swMin(latDegs: Int, latMinutes: Int, longDegs: Int, longMinutes: Int): LatLong = neMin(-latDegs, -latMinutes, -longDegs, -longMinutes)
   //def seqFromDegs(inp: Double*): Seq[LatLong] = inp.mapBy2((lat, long) => LatLong(lat.degreesToRadians, long.degreesToRadians))
   
//   implicit class LatLongSeqImplicit(thisSeq: Seq[LatLong])
//   {
//      def toLatLongLines: Seq[LatLongLine] = (thisSeq.length > 1).ifSeq(
//            {
//               var start: LatLong = thisSeq.head               
//               var acc: Seq[LatLongLine] = Seq()
//               thisSeq.tail.foreach(el => {acc :+= LatLongLine(start, el); start = el})
//               acc
//            })
//   }
}

class LatLongs(val arr: Array[Double]) extends AnyVal with ProductD2s[LatLong]
{ //override def typeStr: String = "LatLongs"
  override def newElem(d1: Double, d2: Double): LatLong = LatLong.apply(d1, d2)
}

object LatLongs extends ProductD2sCompanion[LatLong, LatLongs]
{
  implicit val factory: Int => LatLongs = i => new LatLongs(new Array[Double](i * 2))
  //override def fromArray(value: Array[Double]): LatLongs = new LatLongs(value)
}
