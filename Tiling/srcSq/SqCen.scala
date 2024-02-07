/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A Square tile centre square grid [[SqGrid]] coordinate. */
case class SqCen(r: Int, c: Int) extends SqCenOrSep with TCen
{ override def typeStr: String = "Sqcen"

  def v1: SqVert = SqVert(1, 1)
  def v2: SqVert = SqVert(-1, 1)
  def v3: SqVert = SqVert(-1, -1)
  def v4: SqVert = SqVert(-1, -2)

  /** The vertex sequence in [[SqVert]] coordinates. This starts with the upper right vertex and proceeds clockwise to the upper vertex. */
  def verts: SqVertArr = SqCen.vertsOfSq00.map(sv => sv + this)

  /** The polygon of this tile, specified in [[SqVert]] coordinates. */
  def sqVertPolygon: PolygonSqC = verts.toPolygon

  /** Needs replcing as doesn't take [[SqGridSys]]. Returns the [[LineSegSC]], a line segment specified in [[SqCoord]]s, given by the step. */
  def segStepToOld(st: SqStep): LineSegSC = new LineSegSC(r, c, r + st.tr, c + st.tc)

  /** [[SqCenStep]] for this [[HCen]] and the parameter [[SqStep]]. */
  def andStep(dirn: SqStep): SqCenStep = SqCenStep(r, c, dirn)

  /** Optionally returns the Step value of the SqCen if it is an adjacent SqCen else returns the safe null value. */
  def findStep(operand: SqCen): Option[SqStep] = scSteps.optFind(_.sqCenDelta == operand - this)

  def -(operand: SqCen): SqCen = SqCen(r - operand.r, c - operand.c)

  /** Step to adjacent hex tile. */
  def stepOpt(st: SqStep)(implicit gridSys: SqGridSys): Option[SqCen] = {
    val target = SqCen(r + st.tr, c + st.tc)
    ife(gridSys.sqCenExists(target), Some(target), None)
  }

  /** The polygon of this hex tile if it is part of a regular grid. */
  def polygonReg: Polygon = verts.mapPolygon(_.toPt2Reg)

  def fill(colour: Colour): PolygonFill = polygonReg.fill(colour)

  def active(id: AnyRef = this): PolygonActive = polygonReg.active(id)
}

object SqCen
{ /** implicit [[Show]] type class instance / evidence for [[SqCen]]s. */
  implicit val showEv: ShowTellInt2[SqCen] = ShowTellInt2[SqCen]("SqCen")

  /** implicit [[Unshow]] type class instance / evidence for [[SqCen]]s. */
  implicit val unshowEv: UnshowInt2[SqCen] = UnshowInt2[SqCen]("SqCen", "r", "c", SqCen(_, _))

  val s00v1: SqVert = SqVert(1, 1)
  val s00v2: SqVert = SqVert(-1, 1)
  val s00v3: SqVert = SqVert(-1, -1)
  val s00v4: SqVert = SqVert(1, -1)

  val vertsOfSq00: SqVertArr = SqVertArr(s00v1, s00v2, s00v3, s00v4)

  /** Implicit [[BuilderArrMap]] type class instance / evidence for [[SqCen]] and [[SqCenArr]]. */
  implicit val buildEv: BuilderArrInt2Map[SqCen, SqCenArr] = new BuilderArrInt2Map[SqCen, SqCenArr]
  { type BuffT = SqCenBuff
    override def fromIntArray(array: Array[Int]): SqCenArr = new SqCenArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqCenBuff = new SqCenBuff(buffer)
  }
}

/** An efficient array[Int] based collection for [[SqCen]]s hex grid centre coordinates. */
class SqCenArr(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt2[SqCen]
{ type ThisT = SqCenArr
  override def newElem(int1: Int, int2: Int): SqCen = SqCen(int1, int2)
  override def fromArray(array: Array[Int]): SqCenArr = new SqCenArr(array)
  override def typeStr: String = "SqCens"
  override def fElemStr: SqCen => String = _.toString

  def ===(operand: SqCenArr): Boolean = arrayUnsafe.sameElements(operand.arrayUnsafe)
}

object SqCenPairArr1
{
  def unapply[A](inp: SqCenPairArr[A]): Option[(SqCen, A)] = inp match{
    case ha if ha.length == 1 => Some((ha.a1Index(0), ha.a2Index(0)))
    case _ => None
  }
}
class SqCenBuff(val unsafeBuffer: ArrayBuffer[Int] = BufferInt()) extends AnyVal with BuffInt2[SqCen]
{ type ArrT = SqCenArr
  override def typeStr: String = "SqCenBuff"
  override def newElem(i1: Int, i2: Int): SqCen = SqCen(i1, i2)
}

object SqCenBuff
{ def apply(length: Int = 4): SqCenBuff = new SqCenBuff(new ArrayBuffer[Int](length * 2))
}

class SqCenPair[A2](val a1Int1: Int, val a1Int2: Int, val a2: A2) extends PairInt2Elem[SqCen, A2]
{ override def a1: SqCen = SqCen(a1Int1, a1Int2)
  override def toString: String = s"$a2; $a1Int1, $a1Int2"
}

object SqCenPair
{ def apply[A2](hc: SqCen, a2: A2): SqCenPair[A2] = new SqCenPair[A2](hc.int1, hc.int2, a2)
  def unapply(inp: Any): Option[(SqCen, Any)] = inp match{
    case hcp: SqCenPair[_] => Some((hcp.a1, hcp.a2))
    case _ => None
  }
}

class SqCenPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends ArrPairInt2[SqCen, SqCenArr, A2, SqCenPair[A2]]
{ override type ThisT = SqCenPairArr[A2]
  override def typeStr: String = "SqCenPairArr"
  override def newFromArrays(newA1Array: Array[Int], newA2Array: Array[A2]): SqCenPairArr[A2] = new SqCenPairArr[A2](newA1Array, newA2Array)
  override def newPair(int1: Int, int2: Int, a2: A2): SqCenPair[A2] = new SqCenPair[A2](int1, int2, a2)
  override def newA1(int1: Int, int2: Int): SqCen = SqCen(int1, int2)
  override def a1Arr: SqCenArr = new SqCenArr(a1ArrayInt)
  override def fElemStr: SqCenPair[A2] => String = _.toString
  def SqCenArr: SqCenArr = new SqCenArr(a1ArrayInt)
  def headSqCen: SqCen = SqCen(a1ArrayInt(0), a1ArrayInt(1))
}