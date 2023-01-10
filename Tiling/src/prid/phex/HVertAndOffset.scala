/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HVert]] direction of offset towards [[HCen]]. These objects should not be confused with [[HStep]]s */
sealed trait HVDirn
{ /** The delta in R to the [[HCen]] from an [[HCoord]]. */
  def dCenR: Int

  /** The delta in C to the [[HCen]] from an [[HCoord]]. */
  def dCenC: Int

  def dVertR: Int
  def dVertC: Int

  def intValue: Int

  def opposite: HVDirn
}

object HVDirn
{
  def fromInt(inp: Int): HVDirn = inp match
  { case 0 => HVExact
    case 1 => HVUR
    case 2 => HVDR
    case 3 => HVDn
    case 4 => HVDL
    case 5 => HVUL
    case 6 => HVUp
    case n => excep(s"$n is an invalid Int value for an HVDirn.")
  }
}

/** Hex Vert offset of none. */
object HVExact extends HVDirn
{ def dCenR: Int = 0
  def dCenC: Int = 0
  override def intValue: Int = 0
  override def opposite: HVDirn = HVExact
  override def dVertR: Int = 0
  override def dVertC: Int = 0
}

/** Hex Vert Up offset. */
object HVUp extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 0
  override def intValue: Int = 6
  override def opposite: HVDirn = HVDn
  override def dVertR: Int = 2
  override def dVertC: Int = 0
}

/** Hex Vert Up Right offset. */
object HVUR extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 2
  override def intValue: Int = 1
  override def opposite: HVDirn = HVDL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
}

object HVDR extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 2
  override def intValue: Int = 2
  override def opposite: HVDirn = HVUL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
}

object HVDn extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 0
  override def intValue: Int = 3
  override def opposite: HVDirn = HVUp
  override def dVertR: Int = -2
  override def dVertC: Int = 0
}

object HVDL extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = -2
  override def intValue: Int = 4
  override def opposite: HVDirn = HVUR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
}

object HVUL extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = -2
  override def intValue: Int = 5
  override def opposite: HVDirn = HVDR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
}

class HVertOffset(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirn = HVDirn.fromInt(int1 %% 8)
  def offset: Int = int1 / 8
}

class HVertOffsetSys(val unsafeArray: Array[Int])
{
  //def apply(hCen: HCen, vertNum: Int)
}

/** Offset of an [[HVert]] measured in an offset towards a neighbouring [[HCen]]. */
class HVertAndOffset(val r: Int, val c: Int, val hvDirnInt: Int, val offset: Int)
{ /** The [[HVert]]. */
  def vert: HVert = HVert(r, c)

  def hvDirn: HVDirn = HVDirn.fromInt(hvDirnInt)

  def hCen2 = HCen(r + hvDirn.dCenR, c + hvDirn.dCenC)
  def hVert2 = HVert(r + hvDirn.dVertR, c + hvDirn.dVertC)
  def hVert3 = HVert(r - hvDirn.dVertR, c - hvDirn.dVertC)

  def isCenDirn: Boolean = hvDirn match {
    case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
    case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2  => true
    case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
    case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem2 => true
    case _ => false
  }


  def toPt2Reg(f: HCoord => Pt2)(implicit hSys: HGridSys): Pt2 =
  {
    val p1 = f(vert)
    isCenDirn match
    { case _ if hvDirn == HVExact => p1

      case true if hSys.hCenExists(hCen2) =>
      { val p2 = f(hCen2)
        val x = ((8 - offset) * p1.x + offset * p2.x) / 8
        val y = ((8 - offset) * p1.y + offset * p2.y) / 8
        Pt2(x, y)
      }

      case true =>
      { val p2 = f(hVert3)
        val x = ((8 - offset) * p1.x + offset * p2.x) / 8
        val y = ((8 - offset) * p1.y + offset * p2.y) / 8
        Pt2(x, y)
      }

      case _ =>
      { val p2 = f(hVert2)
        val x = ((8 - offset) * p1.x + offset * p2.x) / 8
        val y = ((8 - offset) * p1.y + offset * p2.y) / 8
        Pt2(x, y)
      }
    }

  }
}

object HVertAndOffset
{
  def apply(hVert: HVert, hvDirn: HVDirn, offset: Int): HVertAndOffset = apply(hVert.r, hVert.c, hvDirn, offset)

  def apply(r: Int, c: Int, hvDirn: HVDirn, offset: Int): HVertAndOffset =
  { val offset2 = ife(offset < 0, -offset, offset)
    val dirn2 = ife(offset < 0, hvDirn.opposite, hvDirn)

    val isCenDirn: Boolean = hvDirn match {
      case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
      case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2 => true
      case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
      case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem0 => true
      case _ => false
    }

    val offset3 = ife(isCenDirn, offset2.min(7), offset2.min(3))
    new HVertAndOffset(r, c, hvDirn.intValue, offset3)
  }

  def none(r: Int, c: Int) = new HVertAndOffset(r, c, 0, 0)
  def none(hVert: HVert) = new HVertAndOffset(hVert.r, hVert.c, 0, 0)
}

class HVOffsets(val int1: Int) extends AnyVal{
  def v1(hVert: HVert): HVertAndOffset ={
    val dirn = HVDirn.fromInt((int1 %% 32) / 4)
    val magnitude = (int1 %% 256) / 32
    HVertAndOffset(hVert, dirn, magnitude)
  }

  def v2(hVert: HVert): HVertAndOffset = {
    val dirn = HVDirn.fromInt((int1 %% 1024) / 256)
    val magnitude = (int1 %% 8192) / 1024
    HVertAndOffset(hVert, dirn, magnitude)
  }

  def verts(hVert: HVert) = int1 %% 4 match {
    case 0 => RArr(HVertAndOffset.none(hVert))
    case 1 => RArr(v1(hVert))
    case 2 => RArr(v1(hVert), v2(hVert))
    case n  => excep(s"$n is an invalid value for offsets.")
  }
}

class HVertOffsetLayer(val unsafeArray: Array[Int])
{

}

class  LineSegHVertAndOffset extends LineSegLike[HVertAndOffset]
{
  /** The start point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  override def startPt: HVertAndOffset = ???

  /** The end point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  override def endPt: HVertAndOffset = ???
}

class HVertAndOffsetPolygon extends PolygonLike[HVertAndOffset]
{
  override type ThisT = HVertAndOffsetPolygon
  override type SideT = LineSegHVertAndOffset

  /** The number of vertices and also the number of sides in this Polygon. */
  override def vertsNum: Int = ???

  /** Performs the side effecting function on the value of each vertex. */
  override def vertsForeach[U](f: HVertAndOffset => U): Unit = ???

  /** Index with foreach on each vertx. Applies the side effecting function on the index with the value of each vertex. Note the function signature
   * follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element)
   * => B signature. */
  override def vertsIForeach[U](f: (Int, HVertAndOffset) => Any): Unit = ???

  /** Maps the vertices of this polygon to an immutable Array like sequence of type B.
   *
   * @tparam B    The element type of the returned sequence.
   * @tparam ArrB The type of the immutable Array like sequence of B.
   * @return the immutable sequence collection by applying the supplied function to each vertex. */
  override def vertsMap[B, ArrB <: Arr[B]](f: HVertAndOffset => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = ???

  /** Folds over the vertices.
   *
   * @tparam B type of the accumulator return value of this method. */
override def vertsFold[B](init: B)(f: (B, HVertAndOffset) => B): B = ???

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
override def vert(index: Int): HVertAndOffset = ???

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
override def vertsPrevForEach[U](f: (HVertAndOffset, HVertAndOffset) => U): Unit = ???
override def sidesForeach[U](f: LineSegHVertAndOffset => U): Unit = ???

  /** Accesses the specifying sequence element by a 0 based index. */
override def ssIndex(index: Int): HVertAndOffset = ???

  /** The number of data elements in the defining sequence. These collections use underlying mutable Arrays and ArrayBuffers. The length of the
   * underlying Array will be a multiple of this number. */
override def ssLength: Int = ???

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
override def unsafeSetElem(i: Int, newElem: HVertAndOffset): Unit = ???
override def fElemStr: HVertAndOffset => String = ???

  /** String specifying the type of this object. */
override def typeStr: String = ???
}