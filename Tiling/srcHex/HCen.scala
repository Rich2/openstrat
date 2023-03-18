/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A Hex tile centre hex grid [[HGrid]] coordinate. This is the tile coordinate and is all that's needed for simple grids, but is usually referred to
 *  as an [[HCen]] to destingush it form [[HSide]]s, [[HVert]]s and [[HCoordOther]]s In Function parameters, the convention is to place the [[HCen]]s
 *  as the first parameter for example  f: (HCen, Pt2) => B. */
class HCen(val r: Int, val c: Int) extends HCenOrSide with TCen
{
  override def equals(that: Any): Boolean = that match {
    case that: HCen if r == that.r & c == that.c => true
    case _ => false
    }

  def v0: HVert = HVert(r + 1 , c)
  def v1: HVert = HVert(r + 1, c + 2)
  def v2: HVert = HVert(r - 1, c + 2)
  def v3: HVert = HVert(r - 1, c)
  def v4: HVert = HVert(r - 1 , c - 2)
  def v5: HVert = HVert(r + 1 , c - 2)

  /** The vertex sequence in [[HVert]] coordinates. This starts with the upper right vertex and proceeds clockwise to the upper vertex. */
  def verts: HVertArr = HCen.vertsOfHex00.map(hv => hv.addHCen(this))

  /** The polygon of this tile, specified in [[HVert]] coordinates. */
  def hVertPolygon: PolygonHC = verts.toPolygon

  /** The polygon of this hex tile if it is part of a regular grid. */
  def polygonReg: Polygon = verts.mapPolygon(_.toPt2Reg)

  def v0Offset(dirn: HVDirnOpt, magnitude: Int): HVAndOffset = HVAndOffset(r + 1, c, dirn, magnitude)
  def v1Offset(dirn: HVDirnOpt, magnitude: Int): HVAndOffset = HVAndOffset(r + 1, c + 2, dirn, magnitude)
  def v2Offset(dirn: HVDirnOpt, magnitude: Int): HVAndOffset = HVAndOffset(r - 1, c + 2, dirn, magnitude)
  def v3Offset(dirn: HVDirnOpt, magnitude: Int): HVAndOffset = HVAndOffset(r - 1, c, dirn, magnitude)
  def v4Offset(dirn: HVDirnOpt, magnitude: Int): HVAndOffset = HVAndOffset(r - 1, c - 2, dirn, magnitude)
  def v5Offset(dirn: HVDirnOpt, magnitude: Int): HVAndOffset = HVAndOffset(r + 1, c - 2, dirn, magnitude)

  def v0In(magnitude: Int): HVAndOffset = HVAndOffset(r + 1, c, HVDn, magnitude)
  def v1In(magnitude: Int): HVAndOffset = HVAndOffset(r + 1, c + 2, HVDL, magnitude)
  def v2In(magnitude: Int): HVAndOffset = HVAndOffset(r - 1, c + 2, HVUL, magnitude)
  def v3In(magnitude: Int): HVAndOffset = HVAndOffset(r - 1, c, HVUp, magnitude)
  def v4In(magnitude: Int): HVAndOffset = HVAndOffset(r - 1, c - 2, HVUR, magnitude)
  def v5In(magnitude: Int): HVAndOffset = HVAndOffset(r + 1, c - 2, HVDR, magnitude)

  def vIn(vert: Int, magnitude: Int): HVAndOffset = vert %% 6 match
  { case 0 => v0In(magnitude)
    case 1 => v1In(magnitude)
    case 2 => v2In(magnitude)
    case 3 => v3In(magnitude)
    case 4 => v4In(magnitude)
    case 5 => v5In(magnitude)
  }

  def v0Exact: HVAndOffset = HVAndOffset(r + 1, c, HVExact, 0)
  def v1Exact: HVAndOffset = HVAndOffset(r + 1, c + 2, HVExact, 0)
  def v2Exact: HVAndOffset = HVAndOffset(r - 1, c + 2, HVExact, 0)
  def v3Exact: HVAndOffset = HVAndOffset(r - 1, c, HVExact, 0)
  def v4Exact: HVAndOffset = HVAndOffset(r - 1, c - 2, HVExact, 0)
  def v5Exact: HVAndOffset = HVAndOffset(r + 1, c - 2, HVExact, 0)

  def vExact(vert: Int): HVAndOffset = vert %% 6 match
  { case 0 => v0Exact
    case 1 => v1Exact
    case 2 => v2Exact
    case 3 => v3Exact
    case 4 => v4Exact
    case 5 => v5Exact
  }

  def vertsIn(magnitude: Int): PolygonHVAndOffset =
    PolygonHVAndOffset(v0In(magnitude), v1In(magnitude), v2In(magnitude), v3In(magnitude), v4In(magnitude), v5In(magnitude))

  def fill(colour: Colour): PolygonFill = polygonReg.fill(colour)
  def active(id: AnyRef = this): PolygonActive = polygonReg.active(id)
  override def typeStr: String = "HCen"

  /** This method needs removal. Step to adjacent hex tile. Will throw exception on illegal value. */
  def unsafeStepDepr(st: HStep)(implicit grider: HGridSys): HCen = grider.unsafeStepEnd(this, st)// HCen(r + st.r, c + st.c)

  /** Step to adjacent hex tile. */
  def stepOpt(st: HStep)(implicit grider: HGridSys): Option[HCen] = grider.findStepEnd(this, st)

  /** HCenStep for this [[HCen]] and the parameter [[HStep]]. */
  def andStep(step: HStep): HCenStep = HCenStep(r, c, step)

  /** Returns the [[LineSegHC]], a line segment specified in [[HCoord]]s, given by the step. */
  def segStepTo(st: HStep): LineSegHC = new LineSegHC(r, c, r + st.tr, c + st.tc)

  def stepToUnsafe(step: HStep): HCen = new HCen(r + step.tr, c + step.tc)

  /** I don't like this method,at least with this operator. */
  def -(operand: HCen): HCen = HCen(r - operand.r, c - operand.c)

 // def text32(fontSize: Double = 12, colour: Colour = Black) = this.strComma.toTextGraphic(fontSize, toPt2Reg, colour)

  def neibs: HCenArr = HCen.neibs00.map(n => HCen(r + n.r, c + n.c))
}

/** Companion object of HCen trait, contains HVert values for hex tile 0, 0. As well as apply method and Show implicit. */
object HCen
{
  def apply(r: Int, c: Int): HCen = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case _ => debexc(s"$r, $c is not a valid Hex centre tile coordinate.")
  }

  def unapply(input: HCoord): Option[(Int, Int)] = input match
  { case hc: HCen => Some((hc.r, hc.c))
    case _ => None
  }

  val h00v0: HVert = HVert(1, 0)
  val h00v1: HVert = HVert(1, 2)
  val h00v2: HVert = HVert(-1, 2)
  val h00v3: HVert = HVert(-1, 0)
  val h00v4: HVert = HVert(-1, -2)
  val h00v5: HVert = HVert(1, -2)

  val vertsOfHex00: HVertArr = HVertArr(h00v0, h00v1, h00v2, h00v3, h00v4, h00v5)

  val neibs00: HCenArr = HCenArr(HCen(2, 2), HCen(0, 4), HCen(-2, 2), HCen(-2, -2), HCen(0, -4), HCen(2, -2))

  /** implicit [[Persist]] instance / evidence for [[HCen]]. */
  implicit val persistEv: Persist[HCen] = new PersistShowInt2[HCen]("HCen", "r", "c", HCen(_, _))

  /** Implicit [[ArrMapBuilder]] type class instance / evidence for [[HCen]] and [[HCenArr]]. */
  implicit val arrMapBuilderEv: Int2ArrMapBuilder[HCen, HCenArr] = new Int2ArrMapBuilder[HCen, HCenArr]
  { type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): HCenArr = new HCenArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCenBuff = new HCenBuff(buffer)
  }

  implicit def pairArrMapBuilder[B2](implicit ct: ClassTag[B2]): HCenPairArrMapBuilder[B2] = new HCenPairArrMapBuilder[B2]
}

/** An efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates. */
class HCenArr(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[HCen]
{ type ThisT = HCenArr
  override def newElem(int1: Int, int2: Int): HCen = HCen(int1, int2)
  override def fromArray(array: Array[Int]): HCenArr = new HCenArr(array)
  override def typeStr: String = "HCens"
  override def fElemStr: HCen => String = _.toString

  def ===(operand: HCenArr): Boolean = unsafeArray.sameElements(operand.unsafeArray)

  /** Converts a sequence of adjacent hex tiles to hex directions. Only call this method if you are certain the tiles are adjacent. */
  def unsafeToHDirns(implicit grider: HGridSys): HStepArr = ???
}

/** Companion object for [[HCenArr]] trait efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates, contains factory apply and uninitialised methods.. */
object HCenArr extends Int2SeqLikeCompanion[HCen, HCenArr]
{
  //override def buff(initialSize: Int): HCenBuff = new HCenBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): HCenArr = new HCenArr(array)

  implicit object PersistImplicit extends PersistArrInt2s[HCen, HCenArr]("HCens")
  { override def fromArray(value: Array[Int]): HCenArr = new HCenArr(value)
    override def showDecT(obj: HCenArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit flatMap builder instance / evidence for [[HCenArr]]. */
  implicit val flatBuilderEv: Int2ArrFlatBuilder[HCenArr] = new Int2ArrFlatBuilder[HCenArr]
  { type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): HCenArr = new HCenArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCenBuff = new HCenBuff(buffer)
  }
}

class HCenBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[HCen]
{ type ArrT = HCenArr
  override def typeStr: String = "HCenBuff"
  override def newElem(i1: Int, i2: Int): HCen = HCen(i1, i2)
}

object HCenBuff
{ def apply(length: Int = 4): HCenBuff = new HCenBuff(new ArrayBuffer[Int](length * 2))
}

class HCenPair[A2](val a1Int1: Int, val a1Int2: Int, val a2: A2) extends Int2PairElem[HCen, A2]
{ override def a1: HCen = HCen(a1Int1, a1Int2)

  override def toString: String = s"$a2; $a1Int1, $a1Int2"
}

object HCenPair
{ def apply[A2](hc: HCen, a2: A2): HCenPair[A2] = new HCenPair[A2](hc.int1, hc.int2, a2)
  def unapply(inp: Any): Option[(HCen, Any)] = inp match{
    case hcp: HCenPair[_] => Some((hcp.a1, hcp.a2))
    case _ => None
  }
}

class HCenPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends Int2PairArr[HCen, HCenArr, A2, HCenPair[A2]]
{ override type ThisT = HCenPairArr[A2]
  override def typeStr: String = "HCenPairArr"
  override def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): HCenPairArr[A2] = new HCenPairArr[A2](a1ArrayInt, a2Array)
  override def newPair(int1: Int, int2: Int, a2: A2): HCenPair[A2] = new HCenPair[A2](int1, int2, a2)
  override def newA1(int1: Int, int2: Int): HCen = HCen(int1, int2)
  override def a1Arr: HCenArr = new HCenArr(a1ArrayInt)
  override def fElemStr: HCenPair[A2] => String = _.toString
}

class HCenPairBuff[B2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[B2]) extends Int2PairBuff[HCen, B2, HCenPair[B2]]
{ override type ThisT = HCenPairBuff[B2]
  override def typeStr: String = "HCenPairBuff"
  override def newElem(int1: Int, int2: Int, a2: B2): HCenPair[B2] = new HCenPair[B2](int1, int2, a2)
}

class HCenPairArrMapBuilder[B2](implicit ct: ClassTag[B2]) extends Int2PairArrMapBuilder[HCen, HCenArr, B2, HCenPair[B2], HCenPairArr[B2]]
{ override type BuffT = HCenPairBuff[B2]
  override type B1BuffT = HCenBuff
  override implicit val b2ClassTag: ClassTag[B2] = ct
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): HCenPairBuff[B2] = new HCenPairBuff[B2](a1Buffer, a2Buffer)
  override def b1ArrBuilder: ArrMapBuilder[HCen, HCenArr] = HCen.arrMapBuilderEv
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): HCenPairArr[B2] = new HCenPairArr[B2](b1ArrayInt, b2Array)
  override def newB1Buff(): HCenBuff = HCenBuff()
}