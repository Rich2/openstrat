/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** A value of latitude and longitude stored for the earth, stored in arc seconds. The constructor is private as instances will rarely be constructed
 * from arc second values. "ll" and "LL" will be used as an abbreviation for LatLong in method names.  */
final class LatLong(val dbl1: Double, val dbl2: Double) extends LatLongBase with TellDbl2 with PointDbl2 with ApproxDbl
{ override type ThisT = LatLong
  override type LineSegT = LineSegLL
  override def typeStr: String = "LatLong"
  override def tell1: Double = latDegs
  override def tell2: Double = longDegs
  inline def latMilliSecs: Double = dbl1
  inline def longMilliSecs: Double = dbl2
  override def toString: String = "LatLong".appendParenthSemis(latDegStr, longDegStr)
  def degStr: String = latDegStr.appendCommas(longDegStr)

 def andDirn(dirn: Boolean = true): LatLongDirn = LatLongDirn.milliSecs(dbl1, dbl2, dirn)

  override def str: String = latDegStr.appendCommas(longDegStr)
  def persistName = "LatLong"
  def persistMems: Seq[Double] = Seq(latRadians, longRadians)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[LatLong]

  override def approx(that: Any, precision: Double): Boolean = that match
  { case other: LatLong => dblsApprox(other, precision)
    case _ => false
  }

  override def equals(that: Any): Boolean = that match
  { case that: LatLong => dblsEqual(that)
    case _ => false
  }

  /** This method current does not take account of lines that cross the date line, including the Poles */
  def segsTo(num: Int, toPt: LatLong): Seq[LatLong] =
  { val latDelta = toPt.latRadians - latRadians
    val longDelta = toPt.longRadians - longRadians
    Seq(this) ++ (1 to num).map(i => LatLong.radians(latRadians + i * latDelta, longRadians + i * longDelta))
  }

  /** Moves the value northward from this LatLong. This may involve crossing the North Pole or South Pole if the operand is a negative value. When
   *  moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  override def addLat(delta: AngleVec): LatLong = (latMilliSecs + delta.milliSecs) match
  { //Going over the north Pole
    case a if a > MilliSecsIn90Degs => LatLong.milliSecs(MilliSecsIn180Degs - a, longMilliSecs + MilliSecsIn180Degs)
    //Going over the south Pole from western longitude
    case a if a < -MilliSecsIn90Degs => LatLong.milliSecs(-MilliSecsIn180Degs - a, longMilliSecs + MilliSecsIn180Degs)
    case a => LatLong.milliSecs(a, longMilliSecs)
  }

  /** Subtract the [[AngleVec]] delta parameter from the latitude. */
  override def subLat(delta: AngleVec): LatLong = addLat(-delta)

  /** Add the [[Longitude]] delta parameter to the longitude. */
  def addLong(delta: Longitude): LatLong = addLongMilliSeca(delta.milliSecs)

  /** Add the delta parameter to the longitude. */
  def addLongMilliSeca(delta: Double): LatLong =
  { val long1 = longMilliSecs + delta
    val long2 = long1 %+- MilliSecsIn180Degs
    LatLong.milliSecs(latMilliSecs, long2)
  }

  /** Add the [[AngleVec]] delta parameter to the longitude. */
  def addLongVec(delta: AngleVec): LatLong = addLongMilliSeca(delta.milliSecs)

  /** Subtract the [[AngleVec]] delta parameter from the longitude. */
  def subLong(delta: AngleVec): LatLong = addLongVec(-delta)

  def addLongDegs(degsDelta: Double): LatLong = addLongVec(degsDelta.degsVec)
  def subLongDegs(degsDelta: Double): LatLong = addLongDegs(-degsDelta)

  /** Get the XY point from a focus with latitude 0 */
  def xyLat0: Pt2 = Pt2(longRadians.sine * latRadians.sine, latRadians.sine)

  def polyToGlobedArea(inp: PolygonLL): OptEither[PtM2Arr, CurveSegDists] =
  { val d3s: PtM3Arr = inp.ssMap(el => fromFocusMetres(el))
    d3s.earthZPositive
  }

  /** Taking this LatLong as the focus. The focus pont being the point of the Earth that from the view point is at x = 0km aad y = 0km in 2D
   *  coordinates, determines if the parameter point on the globe is Z positive. True if it is facing the viewer false if it is on the far side of the
   *  Earth form the viewer's perspective. */
  def latLongFacing(ll: LatLong): Boolean = fromFocusMetres(ll).z.pos

  /** From focus parameter, converts to 3D metre coordinates. */
  def fromFocusMetres(focus: LatLong): PtM3 = //focus.subLong(longVec).toMetres3.xRotateRadians(-latRadians)
    subLong(focus.longVec).toMetres3.xRotateRadians(-focus.latRadians)

  def fromFocusLineDist3(inp: LineSegLL): LineSegM3 = LineSegM3(
    inp.startPt.subLong(longVec).toMetres3.xRotateRadians(-latRadians),
    inp.endPt.subLong(longVec).toMetres3.xRotateRadians(-latRadians))

  def fromFocusDist2(ll: LatLong): PtM2 = fromFocusMetres(ll).xy

  def optFromFocusDist2(ll: LatLong): Option[PtM2] =
  { val m3 = fromFocusMetres(ll)
    m3.z.pos.toOption(m3.xy)
  }

  def toOptDist2(inp: LatLong): Option[PtM2] =
  { val r1: PtM3 = inp.subLong(longVec).toMetres3.xRotateRadians(-latRadians)
    r1.toXYIfZPositive
  }

  /** Converts to [[PtM3]] where 0°N 0°E is the max Z value 90°N is the max Y value, 0°N 90°E is the max X value. */
  def toMetres3: PtM3 =
  { /** This factor reduces the value of X and Z as latitudes move towards the Poles. */
    val clat = latRadians.cos.abs
    PtM3(longSine * equatorialRadius * clat, latSine * polarRadius, longCos * equatorialRadius * clat)
  }

  /** Converts to [[PtKm3]] where 0°N 0°E is the max Z value 90°N is the max Y value, 0°N 90°E is the max X value. */
  def toKm3: PtKm3 =
  { /** This factor reduces the value of X and Z as latitudes move towards the Poles. */
    val clat = latRadians.cos.abs
    PtKm3(longSine * equatorialRadius * clat, latSine * polarRadius, longCos * equatorialRadius * clat)
  }

  def lineSegTo(endPt: LatLong): LineSegLL = LineSegLL(this, endPt)
  def lineSegFrom(startPt: LatLong): LineSegLL = LineSegLL(startPt, this)
}

/** Companion object for the [[LatLong]] class. Contains factory methods for the creation of LatLong s.  */
object LatLong
{
  /** Factory apply method for LatLong, creates LatLong from a [[Latitude]] and a [[Longitude]]. */
  def apply(lat: Latitude, long: Longitude): LatLong = milliSecs(lat.milliSecs, long.milliSecs)

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in degrees, where southern and western
   * values are negative. */
  def degs(lat: Double, long: Double): LatLong = milliSecs(lat.degsToMilliSecs, long.degsToMilliSecs)

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in radians, where southern and western
   * values are negative. */
  @inline def radians(latRadians: Double, longRadians: Double): LatLong = milliSecs(latRadians.radiansToMilliSecs, longRadians.radiansToMilliSecs)
  /*{ val lat = ((latRadians + PiOn2) %% Pi1) - PiOn2
    val long = ((longRadians + Pi1) %% Pi2) - Pi1
    LatLong.milliSecs(lat.radiansToMilliSecs, long.radiansToMilliSecs)
  }*/

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in arc seconds of a degree, where
   *  southern and western values are negative. */
  def secs(lat: Double, long: Double): LatLong = milliSecs(lat * 1000, long * 1000)

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in thousands of an arc second of a
   *  degree, where southern and western values are negative. */
  def milliSecs(lat: Double, long: Double): LatLong =
  {
    val lat1 = lat %+- MilliSecsIn180Degs
    val lat2 = lat1 match {
      case l if l > MilliSecsIn90Degs => MilliSecsIn180Degs - l
      case l if l < - MilliSecsIn90Degs => -MilliSecsIn180Degs - l
      case l => l
    }
    val long1 = long %+- MilliSecsIn180Degs
    val long2 = long1 match {
      case lo if lo >= 0 & (lat1 > MilliSecsIn90Degs | lat1 < -MilliSecsIn90Degs) => -MilliSecsIn180Degs + lo
      case lo if lo < 0 & (lat1 > MilliSecsIn90Degs | lat1 < -MilliSecsIn90Degs) => MilliSecsIn180Degs + lo
      case lo => lo
    }
    new LatLong(lat2, long2)
  }

  implicit val defaultValueImplicit: DefaultValue[LatLong] = new DefaultValue[LatLong] { override def default: LatLong = LatLong0 }

  /** [[Show]] type class instance / evidence for [[LatLong]]. */
  implicit val showEv: ShowTellDbl2[LatLong] = ShowTellDbl2[LatLong]("LatLong")

  /** [[Unshow]] type class instance / evidence for [[LatLong]]. */
  implicit val unshowEv: UnshowDbl2[LatLong] = UnshowDbl2[LatLong]("LatLong", "lat", "long", LatLong.degs)

  implicit val eqTImplicit: EqT[LatLong] = Eq2DblsT(_.dbl1, _.dbl2)

  implicit val arrMapBuilderImplicit: BuilderArrDbl2Map[LatLong, LatLongArr] = new BuilderArrDbl2Map[LatLong, LatLongArr]
  { override type BuffT = LatLongBuff
    override def fromDblArray(array: Array[Double]): LatLongArr = new LatLongArr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): LatLongBuff = new LatLongBuff(inp)
  }

  implicit val linePathBuildImplicit: LinePathDbl2Builder[LatLong, LinePathLL] = new LinePathDbl2Builder[LatLong, LinePathLL]
  { override type BuffT = LatLongBuff
    override def fromDblArray(array: Array[Double]): LinePathLL = new LinePathLL(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): LatLongBuff = new LatLongBuff(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2BuilderMap[LatLong, PolygonLL] = new PolygonDbl2BuilderMap[LatLong, PolygonLL]
  { override type BuffT = LatLongBuff
    override def fromDblArray(array: Array[Double]): PolygonLL = new PolygonLL(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): LatLongBuff = new LatLongBuff(inp)
  }

  implicit def polygonLLPairbuildImplicit[A2](implicit ct: ClassTag[A2]): PolygonLLPairBuilder[A2] = new PolygonLLPairBuilder[A2]
  implicit val lineSegEv: LineSegLikeBuilderMap[LatLong, LineSegLL] = LineSegLL(_, _)
}