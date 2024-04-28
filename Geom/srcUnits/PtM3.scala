/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** 3 dimensional point specified using metres [[Metres]] as units rather than pure numbers. The Letter M was used rather L for Length to avoid
 *  confusion with the LL ending which is short for Latitude-longitude. */
final class PtM3 private(val xMetresNum: Double, val yMetresNum: Double, val zMetresNum: Double) extends PtLength3
{ override type ThisT = PtM3
  override type LineSegT = LineSegM3
  def typeStr: String = "Metres3"
  override def toString: String = typeStr.appendParenthSemis(xMetresNum.str2, yMetresNum.str2, zMetresNum.str2)
  def kmStr: String = typeStr.appendParenthSemis((xMetresNum / 1000).str2, (yMetresNum / 1000).str2, (zMetresNum / 1000).str2)

  def dbl1: Double = xMetresNum
  def dbl2: Double = yMetresNum
  def dbl3: Double = zMetresNum
  override def xKilometresNum: Double = xMetresNum / 1000
  override def yKilometresNum: Double = yMetresNum / 1000
  override def zKilometresNum: Double = zMetresNum / 1000
  def x: Metres = Metres(xMetresNum)
  def y: Metres = Metres(yMetresNum)
  def z: Metres = Metres(zMetresNum)

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: PtM3): MetresSq = x * operand.x + y * operand.y + z * operand.z
  def xy: PtM2 = PtM2.metresNum(xMetresNum, yMetresNum)
  def xPos: Boolean = x.pos
  def xNeg: Boolean = x.neg
  def yPos: Boolean = y.pos
  def yNeg: Boolean = y.neg
  def zPos: Boolean = z.pos
  def zNeg: Boolean = z.neg
  def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg)
  def / (operator: Length): Pt3 = Pt3(x / operator, y / operator, z / operator)

  /** Converts this Metres3 point to a Some[Metres2] point of the X and Y values, returns None if the Z value is negative. */
  def toXYIfZPositive: Option[PtM2] = ifZPos(Some(PtM2(x, y)), None)

  /** Rotate this 3D point defined in metres around the X Axis by the given parameter given in radians. Returns a new [[PtM3]] point. */
  def xRotateRadians(rotationRadians: Double): PtM3 =
  { val scalar: Metres = Metres(sqrt(y.metresNum * y.metresNum + z.metresNum * z.metresNum))
    if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " + scalar.toString)

    val ang0 = None match {//As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      case _ if z.neg && y.neg => atan(y / z) + Pi
      case _ if z.neg => Pi + atan(y / z)//The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
      case _ => atan(y / z)//This operates on the standard atan range -Pi/2 to pi/2
    }
    val ang1 = ang0 + rotationRadians
    PtM3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }

  /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
  def rotateX(a: AngleVec): PtM3 = PtM3(x, z * a.sin + y * a.cos, z * a.cos - y * a.sin)

  /** rotates the vector around the Y axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateY90: PtM3 = PtM3(z, y, -x)

  /** Rotates the vector around the Y axis 180 degrees or Pi radians. */
  @inline def rotateY180: PtM3 = PtM3(-x, y, -z)

  /** rotates the vector around the Y axis 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateY270: PtM3 = PtM3(-z, y, x)

  /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
  def rotateY(a: AngleVec): PtM3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateY90
    case a if a == DegVec180 => rotateY180
    case a if a == DegVec270 => rotateY270
    //Pt2(x1 * a.cos - y1 * a.sin, x1 * a.sin + y1 * a.cos)
    case a => PtM3(z * a.sin + x * a.cos, y, z * a.cos - x * a.sin)
  }

  /** rotates the vector around the Z axis, 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotateZ90: PtM3 = PtM3(-y, x, z)

  /** Rotates the vector around the Z axis 180 degrees or Pi radians. */
  @inline def rotateZ180: PtM3 = PtM3(-x, -y, z)

  /** rotates the vector around the Z axis 270 degrees anti clockwise or, 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotateZ270: PtM3 = PtM3(y, -x, z)

  /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
  def rotateZ(a: AngleVec): PtM3 = a match
  { case a if a == Deg0 => this
    case a if a == DegVec90 => rotateZ90
    case a if a == DegVec180 => rotateZ180
    case a if a == DegVec270 => rotateZ270
    case a => PtM3(x * a.cos - y * a.sin, x * a.sin + y * a.cos, z)
  }

  /** The distance in the XY plane from an operand [[PtM2]], the default being from the origin. */
  def xyLengthFrom(operand: PtM2 = PtM2.origin): Metres = {
    val sq = xMetresNum.squared + yMetresNum.squared
    Metres(sq.sqrt)
  }

  override def lineSegTo(endPt: PtLength3): LineSegM3 =
    LineSegM3.metresNum(xMetresNum, yMetresNum, zMetresNum, endPt.xMetresNum, endPt.yMetresNum, endPt.zMetresNum)

  override def lineSegFrom(startPt: PtLength3): LineSegM3 =
    LineSegM3.metresNum(startPt.xMetresNum, startPt.yMetresNum, startPt.zMetresNum, xMetresNum, yMetresNum, zMetresNum)
}

/** Companion object for the [[PtM3]] class. the 3D point measure in metres length. */
object PtM3
{ /** Factory apply method for constructing 3D points specified in [[Metres]], from its component axes specified in [[Length]]s. if you want to construct from
   * scalars use the metresNum method. */
  def apply(x: Length, y: Length, z: Length): PtM3 = new PtM3(x.metresNum, y.metresNum, z.metresNum)

  /** Factory method for constructing 3D points specified in [[Metres]], from its component axes measured in numbers of metres. if you want to construct from
   * [[Length]] classes components use the apply method. */
  def metresNum(xMetres: Double, yMetres: Double, zMetres: Double): PtM3 = new PtM3(xMetres, yMetres, zMetres)

  /** Implicit [[BuilderArrMap]] type class instance / evidence for [[PTM3]].  */
  implicit val builderArrEv: BuilderArrDbl3Map[PtM3, PtM3Arr] = new BuilderArrDbl3Map[PtM3, PtM3Arr]
  { type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): PtM3Arr = new PtM3Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(buffer)
  }

  /** [[Show]] type class instance / evidence for [[PTM3]]. */
  implicit lazy val showEv: ShowDbl3[PtM3] = ShowDbl3[PtM3]("PtM3", "x", _.xMetresNum, "y", _.yMetresNum, "z", _.zMetresNum)

  /** [[Unshow]] type class instance / evidence for [[PTM3]]. */
  implicit lazy val unshowEv: UnshowDbl3[PtM3] = UnshowDbl3[PtM3]("PtM3", "x", "y", "z", metresNum)

  /** [[]] */
  implicit def builderArrPairEv[B2](implicit ct: ClassTag[B2]): PtM3PairArrMapBuilder[B2] = new PtM3PairArrMapBuilder[B2]

  /** Implicit instance for the [[PolygonM3Pair]] builder. This has to go in the [[PtM3]] companion object so it can be found by an A => B function
   * where PtM3 is the type B parameter. */
  implicit def polygonPairBuilderImplicit[A2](implicit ct: ClassTag[A2]): PolygonM3PairBuilder[A2] = new PolygonM3PairBuilder[A2]

  implicit val linePathBuildImplicit: LinePathDbl3MapBuilder[PtM3, LinePathM3] = new LinePathDbl3MapBuilder[PtM3, LinePathM3]
  { override type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): LinePathM3 = new LinePathM3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl3BuilderMap[PtM3, PolygonM3] = new PolygonDbl3BuilderMap[PtM3, PolygonM3]
  { override type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(inp)
  }

  implicit val lineSegBuildEv: LineSegLikeBuilderMap[PtM3, LineSegM3] = LineSegM3(_, _)
}

/** Collection class for [[Pt3]]s. Only use this if the more specific [[PolygonM2]] and[[LinePathMs]] classes are not appropriate. */
class PtM3Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl3[PtM3]
{ type ThisT = PtM3Arr
  def fromArray(array: Array[Double]): ThisT = new PtM3Arr(array)
  override def typeStr: String = "Metres3s"
  override def fElemStr: PtM3 => String = _ => "Undefined" //_.str
  override def newElem(d1: Double, d2: Double, d3: Double): PtM3 = PtM3.metresNum(d1, d2, d3)

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

object PtM3Arr extends CompanionSeqLikeDbl3[PtM3, PtM3Arr]
{ override def fromArray(array: Array[Double]): PtM3Arr = new PtM3Arr(array)

  implicit val arrFlatBuilderImplicit: BuilderArrDbl3Flat[PtM3Arr] = new BuilderArrDbl3Flat[PtM3Arr]
  { type BuffT = PtM3Buff
    override def fromDblArray(array: Array[Double]): PtM3Arr = new PtM3Arr(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM3Buff = new PtM3Buff(inp)
  }
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt3]]s collections. */
final class PtM3Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl3Buff[PtM3]
{ override def typeStr: String = "BuffPtMetre3"
  def newElem(d1: Double, d2: Double, d3: Double): PtM3 = PtM3.metresNum(d1, d2, d3)
}

object PtM3Buff
{
  def apply(initSize: Int = 4): PtM3Buff = new PtM3Buff(new ArrayBuffer[Double](initSize * 3))
}

class PtM3Pair[A2](val a1Dbl1: Double, val a1Dbl2: Double, val a1Dbl3: Double, val a2: A2) extends PointDbl3Pair[PtM3, A2]
{ override def a1: PtM3 = PtM3.metresNum(a1Dbl1, a1Dbl2, a1Dbl3)
}

class PtM3PairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl3PairArr[PtM3, PtM3Arr, A2, PtM3Pair[A2]]
{ override type ThisT = PtM3PairArr[A2]
  override def typeStr: String = "PtM3PairArr"
  override def newPair(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): PtM3Pair[A2] = new PtM3Pair[A2](dbl1, dbl2, dbl3, a2)
  override def a1Arr: PtM3Arr = new PtM3Arr(a1ArrayDbl)
  override def fElemStr: PtM3Pair[A2] => String = _.toString
  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): PtM3PairArr[A2] = new PtM3PairArr[A2](a1Array, a2Array)
  override def newA1(dbl1: Double, dbl2: Double, dbl3: Double): PtM3 = PtM3.metresNum(dbl1, dbl2, dbl3)
}

class PtM3PairBuff[B2](val b1DblBuffer: ArrayBuffer[Double], val b2Buffer: ArrayBuffer[B2]) extends BuffPairDbl3[PtM3, B2, PtM3Pair[B2]]
{ override type ThisT = PtM3PairBuff[B2]
  override def typeStr: String = "PtM3PairBuff"
  override def newElem(dbl1: Double, dbl2: Double, dbl3: Double, a2: B2): PtM3Pair[B2] = new PtM3Pair[B2](dbl1, dbl2, dbl3, a2)
}

/** Map builder for [[PtM3PairArr]]s. */
class PtM3PairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends BuilderArrPairDbl3[PtM3, PtM3Arr, B2, PtM3Pair[B2], PtM3PairArr[B2]]
{ override type BuffT = PtM3PairBuff[B2]
  override type B1BuffT = PtM3Buff
  override def b1ArrBuilder: BuilderArrMap[PtM3, PtM3Arr] = PtM3.builderArrEv
  override def arrFromArrAndArray(b1Arr: PtM3Arr, b2s: Array[B2]): PtM3PairArr[B2] = new PtM3PairArr[B2](b1Arr.arrayUnsafe, b2s)
  override def arrFromArrays(a1ArrayDbl: Array[Double], a2Array: Array[B2]): PtM3PairArr[B2] = new PtM3PairArr[B2](a1ArrayDbl, a2Array)
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[B2]): PtM3PairBuff[B2] = new PtM3PairBuff[B2](a1Buffer, a2Buffer)
  override def newB1Buff(): PtM3Buff = PtM3Buff()
}