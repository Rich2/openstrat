/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer, geom.*, reflect.ClassTag

/** A Hex separator coordinate in a Hex Grid.
 * So [[HSepA]] on its primary Hex tile goes from Vert 6 to 1 while it is Side 4 on its secondary Hex tile and goes from Vertex 4 to vertex 3
 * So [[HSepB]] on its primary Hex tile goes from Vert 1 to 2 while it is Side 5 on its secondary Hex tile and goes from Vertex 5 to vertex 4
 * So [[HSepC]] on its primary Hex tile goes from Vert 2 to 3 while it is Side 4 on its secondary Hex tile and goes from Vertex 6 to vertex 4 */
trait HSep extends HCenOrSep, TSep
{ override def typeStr: String = "HSep"

  /** Is a side that goes from top left to bottom right. */
  def isTypeA: Boolean

  /** Is a side that is vertical. Sides 4 for the left hand hex and side 1 for the right hand hex */
  def isTypeB: Boolean

  /** Is a side that goes from top right to bottom left. */
  def isTypeC: Boolean

  /** Returns the hex coordinate Line segment for this Hex Side. */
  def lineSegHC: LSegHC

  /** Returns the upper vertex of this hex side. */
  def vertUpper: HVert

  /** Returns the lower vertex of this hex side. */
  def vertLower: HVert

  /** Returns the 2 adjacent [[HCen]] coordinates of this hex Side. Both tiles may not exist in the [[HGridSysy]].  */
  def unsafeTiles: (HCen, HCen)

  /** Returns the [[HCen]] of the right hand tile needs modification. */
  def tileRt(implicit sys: HGridSys): HCen = sys.sepTileRtUnsafe(this)

  /** Returns the [[HCen]] of the left hand tile needs modification. */
  def tileLt(implicit sys: HGridSys): HCen = sys.sepTileLtUnsafe(this)

  /** Returns the tile and upper vertex index for this side. */
  def tileLtAndVert: (HCen, Int)

  def tileLtAndVertFromRt(rtCenR: Int)(implicit gSys: HGridSys): (HCen, Int)

  /** Returns the [[HCen]] of the right tile and the index of the upper vertex it follows the sides in clockwise direction. */
  def tileRtAndVert: (HCen, Int)

  def tileLtOpt(implicit sys: HGridSys): Option[HCen] = sys.sepTileLtOpt(this)

  def tileRtOpt(implicit sys: HGridSys): Option[HCen] = sys.sepTileRtOpt(this)

  /** The left tile in the [[HGrid]]. It may not exist in the [[HGridSys]], but guaranteed to exist if the side is a link / inner side of an [[HGrid]]. */
  def tileLtReg: HCen

  /** The right tile in the [[HGrid]]. It may not exist in the [[HGridSys]] but guaranteed to exist if the side is a link / inner side of an [[HGrid]]. */
  def tileRtReg: HCen

  /** Not sure about this method. */
  def cornerNums(implicit sys: HGridSys): (HCen, Int, Int)

  def leftCorners(corners: HCornerLayer)(implicit sys: HGridSys): LSegHvOffset

  def rightCorners(corners: HCornerLayer)(implicit sys: HGridSys): LSegHvOffset

  def sideLineHVAndOffSet(corners: HCornerLayer)(using gSys: HGridSys): LSegHvOffset = sideLineHVAndOffSet(gSys, corners)
    
  def sideLineHVAndOffSet(gSys: HGridSys, corners: HCornerLayer): LSegHvOffset =
  { val cs: (HCen, Int, Int) = cornerNums(using gSys)
    corners.sepLineHVAndOffset(gSys, cs._1, cs._2, cs._3)
  }

  def projCornersSideLine(proj: HSysProjection, corners: HCornerLayer): Option[LSeg2] = {
    val ls1: LSegHvOffset = sideLineHVAndOffSet(proj.parent, corners)
    ls1.mapOpt(proj.transOptHVOffset(_))
  }

  /** The angle of alignment that makes a 90 perpendicular angle with this [[HSep]] to the right. */
  def anglePerpRt: Angle

  /** The angle of alignment that makes a 90 perpendicular angle with this [[HSep]] to the right. */
  def anglePerpLt: Angle
}

/** Companion object for the [[HSep]] class, provides an apply factory method that throws an exception for an invalid Hex side coordinate. */
object HSep
{ /** Factory method for HSide that throws an exception for an invalid Hex side coordinate. */
  def apply(r: Int, c: Int): HSep = r %% 4 match
  { case 1 if c.div4Rem1 => new HSepA(r, c)
    case 3 if c.div4Rem3 => new HSepA(r, c)
    case 0 if c.div4Rem2 => new HSepB(r, c)
    case 2 if c.div4Rem0 => new HSepB(r, c)
    case 1 if c.div4Rem3 => new HSepC(r, c)
    case 3 if c.div4Rem1 => new HSepC(r, c)
    case _ => excep(s"$r, $c is not a valid hex side tile coordinate.")
  }

  def unapply(inp: Any): Option[(Int, Int)] = inp match{
    case hs: HSep => Some(hs.r, hs.c)
    case _ => None
  }

  /** Implicit [[BuilderArrMap]] type class instance / evidence for [[HSep]] and [[HSepArr]]. */
  implicit val arrMapBuilderEv: BuilderMapArrInt2[HSep, HSepArr] = new BuilderMapArrInt2[HSep, HSepArr]
  { type BuffT = HSepBuff
    override def fromIntArray(array: Array[Int]): HSepArr = new HSepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HSepBuff = new HSepBuff(buffer)
  }

  /** Implicit [[BuilderArrPair]] type class instances / evidence for [[HSep]]. */
  implicit def builderArrPairEv[B2](using ct2: ClassTag[B2]): HSepBuilderArrPairMap[B2] = new HSepBuilderArrPairMap[B2]

  /** Implicit [[Show]] and [[Unshow]] type class instances / evidence for [[HSep]]. */
  implicit val persistEv: PersistInt2Both[HSep] = PersistInt2Both[HSep]("HSep", "r", _.r, "c", _.c, apply)
}

/** A hex tiles separator that slants down from left to right. r.div4Rem1 & c.div4Rem1 | r.div4Rem3 & c.div4Rem3 */
class HSepA(val r: Int, val c: Int) extends HSep
{ override def isTypeA: Boolean = true
  override def isTypeB: Boolean = false
  override def isTypeC: Boolean = false
  override def vertUpper: HVert = HVert(r, c - 1)
  override def vertLower: HVert = HVert(r, c + 1)
  override def tileLtReg: HCen = HCen(r - 1, c - 1)
  override def tileRtReg: HCen = HCen(r + 1, c + 1)
  override def tileLtAndVert: (HCen, Int) = (HCen(r - 1, c - 1), 0)

  override def tileLtAndVertFromRt(rtCenR: Int)(implicit gSys: HGridSys): (HCen, Int) =
  { val hCen = tileLt
    val i = ife(hCen.r == rtCenR - 2, 0, 2)
    (hCen, i)
  }

  override def tileRtAndVert: (HCen, Int) = (HCen(r + 1, c + 1), 4)
  override def lineSegHC: LSegHC = LSegHC(r, c - 1, r, c + 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r - 1, c - 1), HCen(r + 1, c + 1))
  override def cornerNums(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileLt), (tileLt, 0, 1), (tileRt, 3, 4))

  /** This seems to work. */
  override def leftCorners(corners: HCornerLayer)(using gSys: HGridSys): LSegHvOffset =
    if(gSys.hCenExists(tileLt)) LSegHvOffset(corners.cornerVLast(tileLt, 0), corners.cornerV1(tileLt, 1))
    else LSegHvOffset(tileLt.v0Exact, tileLt.v1Exact)

  /** The vLast doesn't seem to make any difference. */
  override def rightCorners(corners: HCornerLayer)(using gSys: HGridSys): LSegHvOffset =
    if (gSys.hCenExists(tileRt)) LSegHvOffset(corners.cornerVLast(tileRt, 3), corners.cornerV1(tileRt, 4))
    else LSegHvOffset(tileRt.v3Exact, tileRt.v4Exact)

  override def anglePerpRt: Angle = 60.degs
  override def anglePerpLt: Angle = -120.degs
}

object HSepA
{
  def unapply(inp: Any): Option[(Int, Int)] = inp match
  { case hs: HSepA => Some(hs.r, hs.c)
    case _ => None
  }
}

/** A hex tiles separator that slants straight down. r.div4Rem0 & c.div4Rem2 | r.div4Rem2 & c.div4Rem0 */
class HSepB(val r: Int, val c: Int) extends HSep
{ override def isTypeA: Boolean = false
  override def isTypeB: Boolean = true
  override def isTypeC: Boolean = false
  override def vertUpper: HVert = HVert(r + 1, c)
  override def vertLower: HVert = HVert(r - 1, c)
  override def tileLtReg: HCen = HCen(r, c - 2)
  override def tileRtReg: HCen = HCen(r, c + 2)
  override def tileLtAndVert: (HCen, Int) = (HCen(r, c - 2), 1)
  override def tileLtAndVertFromRt(rtCenR: Int)(implicit gSys: HGridSys): (HCen, Int) = (tileLt, 1)

  override def tileRtAndVert: (HCen, Int) = (HCen(r, c + 2), 5)
  override def lineSegHC: LSegHC = LSegHC(r + 1, c, r - 1, c)
  override def unsafeTiles: (HCen, HCen) = (HCen(r, c - 2), HCen(r, c + 2))
  override def cornerNums(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileRt), (tileRt, 4, 5), (tileLt, 1, 2))

  override def leftCorners(corners: HCornerLayer)(implicit sys: HGridSys): LSegHvOffset =
    if (sys.hCenExists(tileLt)) LSegHvOffset(corners.cornerVLast(tileLt, 1), corners.cornerV1(tileLt, 2))
    else LSegHvOffset(tileLt.v1Exact, tileLt.v2Exact)

  override def rightCorners(corners: HCornerLayer)(implicit sys: HGridSys): LSegHvOffset =
    if (sys.hCenExists(tileRt)) LSegHvOffset(corners.cornerVLast(tileRt, 4), corners.cornerV1(tileRt, 5))
    else LSegHvOffset(tileRt.v4Exact, tileRt.v5Exact)

  override def anglePerpRt: Angle = 0.degs
  override def anglePerpLt: Angle = 180.degs
}

object HSepB
{
  def unapply(inp: Any): Option[(Int, Int)] = inp match
  { case hs: HSepB => Some(hs.r, hs.c)
    case _ => None
  }
}

/** A hex tiles separator that slants down from top right to bottom left. r.div4Rem1 & c.div4Rem3 | r.div4Rem3 & c.div4Rem1 */
class HSepC(val r: Int, val c: Int) extends HSep
{ override def isTypeA: Boolean = false
  override def isTypeB: Boolean = false
  override def isTypeC: Boolean = true
  override def vertUpper: HVert = HVert(r, c + 1)
  override def vertLower: HVert = HVert(r, c - 1)
  override def tileLtReg: HCen = HCen(r + 1, c - 1)
  override def tileRtReg: HCen = HCen(r - 1, c + 1)
  override def tileLtAndVert: (HCen, Int) = (HCen(r + 1, c - 1), 2)

  override def tileLtAndVertFromRt(rtCenR: Int)(using gSys: HGridSys): (HCen, Int) =
  { val hCen = tileLt
    val i = ife(hCen.r == rtCenR + 2, 2, 0)
    (hCen, i)
  }

  override def tileRtAndVert: (HCen, Int) = (HCen(r - 1, c + 1), 0)
  override def lineSegHC: LSegHC = LSegHC(r, c + 1, r, c - 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r + 1, c - 1), HCen(r - 1, c + 1))
  override def cornerNums(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileRt),(tileRt, 5, 0),  (tileLt, 2, 3))

  override def leftCorners(corners: HCornerLayer)(using gSys: HGridSys): LSegHvOffset =
    if (gSys.hCenExists(tileLt)) LSegHvOffset(corners.cornerV1(tileLt, 2), corners.cornerV1(tileLt, 3))
    else LSegHvOffset(tileLt.v2Exact, tileLt.v3Exact)

  /** I think this is now correct. */
  override def rightCorners(corners: HCornerLayer)(using gSys: HGridSys): LSegHvOffset =
    if (gSys.hCenExists(tileRt)) LSegHvOffset(corners.cornerVLast(tileRt, 5), corners.cornerV1(tileRt, 0))
    else LSegHvOffset(tileRt.v5Exact, tileRt.v0Exact)

  override def anglePerpRt: Angle = -60.degs
  override def anglePerpLt: Angle = 120.degs
}

object HSepC
{
  def unapply(inp: Any): Option[(Int, Int)] = inp match
  { case hs: HSepC => Some(hs.r, hs.c)
    case _ => None
  }
}

/** [[PairElem]] class for [[HSep]]s. Allows for the efficient storage of sequences in [[HSepArrPair]]s. */
class HSepPair[A2](val a1Int1: Int, val a1Int2: Int, val a2: A2) extends PairInt2Elem[HSep, A2]
{ override def a1: HSep = HSep(a1Int1, a1Int2)
  override def toString: String = s"$a2; $a1Int1, $a1Int2"
}

/** Companion object for [[HSepPair]] trait, provides apply and unapply methods. */
object HSepPair
{ def apply[A2](hc: HSep, a2: A2): HSepPair[A2] = new HSepPair[A2](hc.int1, hc.int2, a2)
  def unapply(inp: Any): Option[(HSep, Any)] = inp match
  { case hcp: HSepPair[_] => Some((hcp.a1, hcp.a2))
    case _ => None
  }
}