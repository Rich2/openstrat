/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** 3 dimensional point specified using [[Kilometres]] as units rather than scalars. */
final class PtKm3(val xKilometresNum: Double, val yKilometresNum: Double, val zKilometresNum: Double) extends PtLength3
{ override type ThisT = PtKm3
  override type LineSegT = LineSegKm3
  def typeStr: String = "PtKm3"
  override def toString: String = typeStr.appendParenthSemis(xKilometresNum.str2, yKilometresNum.str2, zKilometresNum.str2)
  def kmStr: String = typeStr.appendParenthSemis((xKilometresNum / 1000).str2, (yKilometresNum / 1000).str2, (zKilometresNum / 1000).str2)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[Kilometres3]
  def dbl1: Double = xKilometresNum
  def dbl2: Double = yKilometresNum
  def dbl3: Double = zKilometresNum
  override def xMetresNum: Double = xKilometresNum * 1000
  override def yMetresNum: Double = yKilometresNum * 1000
  override def zMetresNum: Double = zKilometresNum * 1000
  def x: Kilometres = Kilometres(xKilometresNum)
  def y: Kilometres = Kilometres(yKilometresNum)
  def z: Kilometres = Kilometres(zKilometresNum)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: PtKm3): KilometresSq = x * operand.x + y * operand.y + z * operand.z
  def xy: PtM2 = PtM2.metresNum(xKilometresNum, yKilometresNum)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Length): Pt3 = Pt3(x / operator, y / operator, z / operator)

  /** Converts this Kilometres3 point to a Some[Kilometres2] point of the X and Y values, returns None if the Z value is negative. */
  def toXYIfZPositive: Option[PtKm2] = ifZPos(Some(PtKm2(x, y)), None)

  /** Rotate this 3D point defined in metres around the X Axis by the given parameter given in radians. Returns a new [[PtKm3]] point. */
  def xRotateRadians(rotationRadians: Double): PtKm3 =
  { val len: Kilometres = Kilometres(sqrt(y.metresNum * y.metresNum + z.metresNum * z.metresNum))
    if(len > EarthEquatorialRadius * 1.05) throw excep("scalar: " + len.toString)

    val ang0 = None match {//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      case _ if z.neg && y.neg => atan(y / z) + Pi
      case _ if z.neg => Pi + atan(y / z)//The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
      case _ => atan(y / z)//This operates on the standard atan range -Pi/2 to pi/2
    }
    val ang1 = ang0 + rotationRadians
    PtKm3(x, len * sin(ang1), len * cos(ang1))
  }

  /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
  def rotateX(a: AngleVec): PtKm3 = PtKm3(x, z * a.sin + y * a.cos, z * a.cos - y * a.sin)

  /** rotates the vector around the Y axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateY90: PtKm3 = PtKm3(z, y, -x)

  /** Rotates the vector around the Y axis 180 degrees or Pi radians. */
  @inline def rotateY180: PtKm3 = PtKm3(-x, y, -z)

  /** rotates the vector around the Y axis 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateY270: PtKm3 = PtKm3(-z, y, x)

  /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
  def rotateY(a: AngleVec): PtKm3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateY90
    case a if a == DegVec180 => rotateY180
    case a if a == DegVec270 => rotateY270
    //Pt2(x1 * a.cos - y1 * a.sin, x1 * a.sin + y1 * a.cos)
    case a => PtKm3(z * a.sin + x * a.cos, y, z * a.cos - x * a.sin)
  }

  /** rotates the vector around the Z axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateZ90: PtKm3 = PtKm3(-y, x, z)

  /** Rotates the vector around the Z axis 180 degrees or Pi radians. */
  @inline def rotateZ180: PtKm3 = PtKm3(-x, -y, z)

  /** rotates the vector around the Z axis 270 degrees anti clockwise or, 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateZ270: PtKm3 = PtKm3(y, -x, z)

  /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
  def rotateZ(a: AngleVec): PtKm3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateZ90
    case a if a == DegVec180 => rotateZ180
    case a if a == DegVec270 => rotateZ270
    case a => PtKm3(x * a.cos - y * a.sin, x * a.sin + y * a.cos, z)
  }

  /** The distance in the XY plane from an operand [[PtM2]], the default being from the origin. */
  def xyLengthFrom(operand: PtM2 = PtM2.origin): Kilometres = {
    val sq = xKilometresNum.squared + yKilometresNum.squared
    Kilometres(sq.sqrt)
  }

  override def lineSegTo(endPt: PtLength3): LineSegKm3 =
    LineSegKm3.kilometresNum(xKilometresNum, yKilometresNum, zKilometresNum, endPt.xKilometresNum, endPt.yKilometresNum, endPt.zKilometresNum)

  override def lineSegFrom(startPt: PtLength3): LineSegKm3 =
    LineSegKm3.kilometresNum(startPt.xKilometresNum, startPt.yKilometresNum, startPt.zKilometresNum, xKilometresNum, yKilometresNum, zKilometresNum)
}

/** Companion object for the [[PtKm3]] the 3 dimensional space point class. Contains factory methods and implicit type class instances. */
object PtKm3
{  /** Factory apply method for constructing 3D points specified in [[Kilometres]], from its component axes specified in [[Length]]s. if you want to construct
   * from scalars use the kilometresNum method. */
  def apply(x: Length, y: Length, z: Length): PtKm3 = new PtKm3(x.kilometresNum, y.kilometresNum, z.kilometresNum)

  /** Factory method for constructing 3D points specified in [[Kilometres]], from its component axes input as [[Length]]s. if you want to construct from
   * [[Length]] classes components use the apply method. */
  def kilometres(xKilometres: Double, yKilometres: Double, zKilometres: Double): PtKm3 = new PtKm3(xKilometres, yKilometres, zKilometres)

  /** [[BuilderArrMap]] type class instance / evidence for [[PtKm3]]. */
  implicit val builderArrEv: BuilderArrDbl3Map[PtKm3, PtKm3Arr] = new BuilderArrDbl3Map[PtKm3, PtKm3Arr]
  { type BuffT = PtKm3Buff
    override def fromDblArray(array: Array[Double]): PtKm3Arr = new PtKm3Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): PtKm3Buff = new PtKm3Buff(buffer)
  }

  /** [[Show]] type class instance / evidence for [[PTKm3]]. */
  implicit lazy val showEv: ShowDbl3[PtKm3] = ShowDbl3[PtKm3]("PtKm3", "x", _.xKilometresNum, "y", _.yKilometresNum, "z", _.zKilometresNum)

  /** [[Unshow]] type class instance / evidence for [[PTKm3]]. */
  implicit lazy val unshowEv: UnshowDbl3[PtKm3] = UnshowDbl3[PtKm3]("PtKm3", "x", "y", "z", kilometres)

  implicit def pairArrBuiderImplicit[B2](implicit ct: ClassTag[B2]): PtKm3PairArrMapBuilder[B2] = new PtKm3PairArrMapBuilder[B2]

  /** Implicit instance for the [[PolygonKm3Pair]] builder. This has to go in the [[PtKm3]] companion object so it can be found by an A => B function where
   * [[PtKm3]] is the type B parameter. */
  implicit def polygonPairBuilderImplicit[A2](implicit ct: ClassTag[A2]): PolygonKm3PairBuilder[A2] = new PolygonKm3PairBuilder[A2]

  implicit val linePathBuildImplicit: LinePathDbl3MapBuilder[PtKm3, LinePathKm3] = new LinePathDbl3MapBuilder[PtKm3, LinePathKm3]
  { override type BuffT = PtKm3Buff
    override def fromDblArray(array: Array[Double]): LinePathKm3 = new LinePathKm3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtKm3Buff = new PtKm3Buff(inp)
  }

  /** [[PolygonLikeBuilderMap]] type class instance evidence for [[PtKm3]]. This is used to map to a [[PolygonKm3]]. */
  implicit val polygonBuildMapEv: PolygonDbl3BuilderMap[PtKm3, PolygonKm3] = new PolygonDbl3BuilderMap[PtKm3, PolygonKm3]
  { override type BuffT = PtKm3Buff
    override def fromDblArray(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtKm3Buff = new PtKm3Buff(inp)
  }

  /** Implicit [[LineSegLikeBuilderMap]] type class instance / evidence for [[PtKm3]] points. Note rhis is used to map to a [[LineSegKm3]] not a
   * [[LinsSegKm3Arr]]. */
  implicit val lineSegBuildEv: LineSegLikeBuilderMap[PtKm3, LineSegKm3] = LineSegKm3(_, _)
}

/** Collection class for [[Pt3]]s. Only use this if the more specific [[PolygonM2]] and[[LinePathMs]] classes are not appropriate. */
class PtKm3Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl3[PtKm3]
{ type ThisT = PtKm3Arr
  def fromArray(array: Array[Double]): ThisT = new PtKm3Arr(array)
  override def typeStr: String = "Metres3s"
  override def fElemStr: PtKm3 => String = _ => "Undefined" //_.str
  override def newElem(d1: Double, d2: Double, d3: Double): PtKm3 = new PtKm3(d1, d2, d3)

  /** This methods function is to work on a sequence of 3d points representing a polygon on the surface a globe (eg the Earth). If Z is positive its
   *  on the side of the Earth that the viewer is looking at. Returns z positive dist2 points if 1 or more of the points are z positive. Z negative
   *  points are moved to the horizon. */
  def earthZPositive: OptEither[PtM2Arr, CurveSegDists] =
  {
    existsCount(_.z.pos) match
    { case 0 => NoOptEither
    case n if n == length => SomeA(map(_.xy))
    case n => NoOptEither
      //      {
      //        var els: List[Either[Dist2, Dist2]] = lMap {
      //          case el if el.z.pos => Right(el.xy)
      //          case el =>
      //          { val xy = el.xy
      //            val fac = xy.magnitude / EarthAvRadius
      //            Left(xy / fac)
      //          }
      //        }
      //        while (els.head.isLeft && els.last.isLeft && els.init.last.isLeft) els = els.init
      //
      //        val els2: List[Either[Dist2, Dist2]] = els.drop(2).foldLeft(els.take(2))((acc, el) => el match
      //          {
      //            case Left(v) if acc.last.isLeft && acc.init.last.isLeft => acc.init :+ el
      //            case el => acc :+ el
      //          })
      //
      //        val acc: CurveSegDists = CurveSegDists.factory(els2.length)// List[CurveSegDist] = Nil
      //        var last: Either[Dist2, Dist2] = els2.last
      //        els2.iForeach {(e, i) =>
      //          e match
      //          { case Right(d2) => acc.setElem(i, LineSegDist(d2))
      //            case Left(d2) if last.isLeft => acc.setElem(i, ArcSegDist(Dist2Z, d2))
      //            case Left(d2) => acc.setElem(i, LineSegDist(d2))
      //          }
      //          last = e
      //        }
      //        GlobedSome(acc)
      //      }
    }
  }
}

object PtKm3Arr extends CompanionSeqLikeDbl3[PtKm3, PtKm3Arr]
{ override def fromArray(array: Array[Double]): PtKm3Arr = new PtKm3Arr(array)

  implicit val arrFlatBuilderImplicit: BuilderArrDbl3Flat[PtKm3Arr] = new BuilderArrDbl3Flat[PtKm3Arr]
  { type BuffT = PtKm3Buff
    override def fromDblArray(array: Array[Double]): PtKm3Arr = new PtKm3Arr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtKm3Buff = new PtKm3Buff(inp)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt3]]s collections. */
final class PtKm3Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl3Buff[PtKm3]
{ override def typeStr: String = "BuffPtMetre3"
  def newElem(d1: Double, d2: Double, d3: Double): PtKm3 = new PtKm3(d1, d2, d3)
}

object PtKm3Buff
{
  def apply(initSize: Int = 4): PtKm3Buff = new PtKm3Buff(new ArrayBuffer[Double](initSize * 3))
}

class PtKm3Pair[A2](val a1Dbl1: Double, val a1Dbl2: Double, val a1Dbl3: Double, val a2: A2) extends PointDbl3Pair[PtKm3, A2]
{ override def a1: PtKm3 = new PtKm3(a1Dbl1, a1Dbl2, a1Dbl3)
}

class PtKm3PairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl3PairArr[PtKm3, PtKm3Arr, A2, PtKm3Pair[A2]]
{ override type ThisT = PtKm3PairArr[A2]
  override def typeStr: String = "PtKm3PairArr"
  override def newPair(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): PtKm3Pair[A2] = new PtKm3Pair[A2](dbl1, dbl2, dbl3, a2)
  override def a1Arr: PtKm3Arr = new PtKm3Arr(a1ArrayDbl)
  override def fElemStr: PtKm3Pair[A2] => String = _.toString
  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): PtKm3PairArr[A2] = new PtKm3PairArr[A2](a1Array, a2Array)
  override def newA1(dbl1: Double, dbl2: Double, dbl3: Double): PtKm3 = new PtKm3(dbl1, dbl2, dbl3)
}

class PtKm3PairBuff[B2](val b1DblBuffer: ArrayBuffer[Double], val b2Buffer: ArrayBuffer[B2]) extends BuffPairDbl3[PtKm3, B2, PtKm3Pair[B2]]
{ override type ThisT = PtKm3PairBuff[B2]
  override def typeStr: String = "PtKm3PairBuff"
  override def newElem(dbl1: Double, dbl2: Double, dbl3: Double, a2: B2): PtKm3Pair[B2] = new PtKm3Pair[B2](dbl1, dbl2, dbl3, a2)
}

/** Map builder for [[PtKm3PairArr]]s. */
class PtKm3PairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends BuilderArrPairDbl3[PtKm3, PtKm3Arr, B2, PtKm3Pair[B2], PtKm3PairArr[B2]]
{ override type BuffT = PtKm3PairBuff[B2]
  override type B1BuffT = PtKm3Buff
  override def b1ArrBuilder: BuilderArrMap[PtKm3, PtKm3Arr] = PtKm3.builderArrEv
  override def arrFromArrAndArray(b1Arr: PtKm3Arr, b2s: Array[B2]): PtKm3PairArr[B2] = new PtKm3PairArr[B2](b1Arr.arrayUnsafe, b2s)
  override def arrFromArrays(a1ArrayDbl: Array[Double], a2Array: Array[B2]): PtKm3PairArr[B2] = new PtKm3PairArr[B2](a1ArrayDbl, a2Array)
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[B2]): PtKm3PairBuff[B2] = new PtKm3PairBuff[B2](a1Buffer, a2Buffer)
  override def newB1Buff(): PtKm3Buff = PtKm3Buff()
}