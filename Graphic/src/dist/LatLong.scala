/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

/** longitude and latitude measured in radians for the earth. "ll" and "LL" will be used as an abbreviation for LatLong in method names.  */
class LatLong private(val latRadians: Double, val longRadians: Double) extends LatLongBase with ProdDbl2
{
  override def toString: String = LatLong.persistImplict.show(this)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[LatLong]
  def _1 = latRadians
  def _2 = longRadians
  def persistName = "LatLong"
  def persistMems = Seq(latRadians, longRadians)  
  def polarRadius: Dist = EarthPolarRadius
  def equatorialRadius: Dist = EarthEquatorialRadius
  def +(other: LatLong): LatLong = addLong(other.longRadians).addLat(other.latRadians)
  def -(other: LatLong): LatLong = addLong(-other.longRadians).addLat(-other.latRadians)

  /** This method current does not take account of lines that cross the date line, including the Poles */
  def segsTo(num: Int, toPt: LatLong): Seq[LatLong] =
  { val latDelta = toPt.latRadians - latRadians
    val longDelta = toPt.longRadians - longRadians
    Seq(this) ++ (1 to num).map(i => LatLong(latRadians + i * latDelta, longRadians + i * longDelta))
   }
   
  def addLat(radians: Double): LatLong = Angle.resetRadians(latRadians + radians) match
  { //Going over the north Pole from western longitude
    case a if a > PiH && longRadians <= 0 => LatLong(Pi - a, longRadians + Pi)
    //Going over the north Pole from an eastern longitude
    case a if a > PiH             =>  LatLong(Pi - a, longRadians - Pi)
    //Going over the south Pole from western longitude
    case a if a < -PiH && longRadians < 0 => LatLong(-Pi - a, Pi + longRadians)
    //Going over the south Pole from eastern longitude
    case a if a < -PiH             => LatLong(-Pi - a, longRadians - Pi)
    case a => LatLong(a, longRadians)
  }
      
  def subLat(radians: Double): LatLong = addLat(-radians)
  def addLong(radians: Double): LatLong = LatLong(latRadians, Angle.resetRadians(longRadians + radians))
  def subLong(radians: Double): LatLong = addLong(-radians)
   
  /** Get the XY point from a focus with latitude 0 */
  def xyLat0: Vec2 = Vec2(sin(longRadians) * cos(latRadians), sin(latRadians))
   
  /** Note this method does not check which side of the earth relative to viewer the polygon verts are */
  def polyToDist2s(inp: LatLongs): Dist2s = inp.pMap(fromFocusDist2)
   
  def polyToGlobedArea(inp: LatLongs): GlobedArea =
  { val d3s: Dist3s = inp.pMap(el => fromFocusDist3(el))
    d3s.earthZPositive
  }
  def latLongFacing(ll: LatLong): Boolean = fromFocusDist3(ll).z.pos
   
  def fromFocusDist3(ll: LatLong): Dist3 = ll.subLong(longRadians).toDist3.xRotation(-latRadians)
  def fromFocusLineDist3(inp: LLLineSeg): LineDist3 = LineDist3(
    inp.llStart.subLong(longRadians).toDist3.xRotation(-latRadians),
    inp.latLong2.subLong(longRadians).toDist3.xRotation(-latRadians))
         
  def fromFocusDist2(ll: LatLong): Dist2 = fromFocusDist3(ll).xy
  def optFromFocusDist2(ll: LatLong): Option[Dist2] =
  { val v3 = fromFocusDist3(ll)
    v3.z.pos.toOption(v3.xy)
  }

  def toOptDist2(inp: LatLong): Option[Dist2] =
  { val r1: Dist3 = inp.subLong(longRadians).toDist3.xRotation(-latRadians)
    r1.toXYIfZPositive
  }
   
  def toVec3(polarRadius: Double, equatorialRadius: Double): Vec3 =
  { val clat = cos(latRadians).abs
    Vec3(sin(longRadians) * equatorialRadius * clat, cos(latRadians) * polarRadius, cos(longRadians) * equatorialRadius * clat)
  }
}

/** Companion object for LatLong. */
object LatLong
{
  @inline def apply(latRadians: Double, longRadians: Double): LatLong =
  { val lat = ((latRadians + Pi / 2) %% Pi) - Pi / 2
    val long = ((longRadians + Pi) %% Pi2) - Pi
    new LatLong(lat, long)
  }

  @inline def radians(latRadians: Double, longRadians: Double): LatLong =
  { val lat = ((latRadians + Pi / 2) %% Pi) - Pi / 2
    val long = ((longRadians + Pi) %% Pi2) - Pi
    new LatLong(lat, long)
  }
   
  implicit val persistImplict: PersistEq[LatLong] =
    new PersistD2[LatLong]("LatLong", "lat", _.latRadians, "long", _.longRadians, this.radians)
   
  //def apply(latAngle: Latitude, longAngle: Longitude): LatLong = new LatLong(latAngle.radians, longAngle.radians)
   //def ll(lat: Latitude, long: Longitude) = new LatLong(lat.radians, long.radians)
   def deg(lat: Double, long: Double): LatLong = LatLong.radians(lat.degreesToRadians, long.degreesToRadians)
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


