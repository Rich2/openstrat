/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** An [[HVert]] and an offset. The Offset of from the [[HVert]] measured in an offset towards a neighbouring [[HCen]] or [[HVert]]. */
class HVAndOffset(val int1: Int, val int2: Int, val int3: Int) extends Int3Elem
{
  inline def r: Int = int1

  inline def c: Int = int2

  /** The [[HVert]]. */
  def vert: HVert = HVert(r, c)

  def hvOffset: HVOffset = HVOffset.fromInt(int3)

  def magnitude: Int = hvOffset.magnitude

  def hvDirn: HVDirn = hvOffset.hvDirn

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
        val x = ((8 - magnitude) * p1.x + magnitude * p2.x) / 8
        val y = ((8 - magnitude) * p1.y + magnitude * p2.y) / 8
        Pt2(x, y)
      }

      case true =>
      { val p2 = f(hVert3)
        val x = ((8 - magnitude) * p1.x + magnitude * p2.x) / 8
        val y = ((8 - magnitude) * p1.y + magnitude * p2.y) / 8
        Pt2(x, y)
      }

      case _ =>
      { val p2 = f(hVert2)
        val x = ((8 - magnitude) * p1.x + magnitude * p2.x) / 8
        val y = ((8 - magnitude) * p1.y + magnitude * p2.y) / 8
        Pt2(x, y)
      }
    }
  }
}

object HVAndOffset
{
  def apply(hVert: HVert, hvDirn: HVDirn, offset: Int): HVAndOffset = apply(hVert.r, hVert.c, hvDirn, offset)

  def apply(r: Int, c: Int, hvDirn: HVDirn, magnitude: Int): HVAndOffset =
  { val magnitude2 = ife(magnitude < 0, -magnitude, magnitude)
    val dirn2 = ife(magnitude < 0, hvDirn.opposite, hvDirn)

    val isCenDirn: Boolean = hvDirn match {
      case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
      case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2 => true
      case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
      case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem0 => true
      case _ => false
    }

    val magnitude3 = ife(isCenDirn, magnitude2.min(7), magnitude2.min(3))

    val hVertOffset = HVOffset(dirn2, magnitude3)
    new HVAndOffset(r, c, hVertOffset)
  }

  def none(r: Int, c: Int) = new HVAndOffset(r, c, 0)
  def none(hVert: HVert) = new HVAndOffset(hVert.r, hVert.c, 0)
}

/** A Line segment where the vertices of specified in [[HVAndOffset]]s. */
class LineSegHVAndOffset extends LineSegLike[HVAndOffset]
{
  /** The start point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  override def startPt: HVAndOffset = ???

  /** The end point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegM2]]. */
  override def endPt: HVAndOffset = ???
}

/** A polygon where the vertices are specified in [[HVAndOffset]]s. */
class HVAndOffsetPolygon(val unsafeArray: Array[Int]) extends PolygonLikeInt3[HVAndOffset]
{ override type ThisT = HVAndOffsetPolygon
  override type SideT = LineSegHVAndOffset
  override def typeStr: String = "HVAndOffsetPolygon"
  override def ssElem(int1: Int, int2: Int, int3: Int): HVAndOffset = new HVAndOffset(int1, int2, int3)
  override def fromArray(array: Array[Int]): HVAndOffsetPolygon = new HVAndOffsetPolygon(array)

  /** Index with foreach on each vertx. Applies the side effecting function on the index with the value of each vertex. Note the function signature
   * follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element)
   * => B signature. */
  override def vertsIForeach[U](f: (Int, HVAndOffset) => Any): Unit = ???

  override def vertsMap[B, ArrB <: Arr[B]](f: HVAndOffset => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = ???

  override def vertsFold[B](init: B)(f: (B, HVAndOffset) => B): B = ???

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  override def vert(index: Int): HVAndOffset = ???

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
override def vertsPrevForEach[U](f: (HVAndOffset, HVAndOffset) => U): Unit = ???
override def sidesForeach[U](f: LineSegHVAndOffset => U): Unit = ???
override def fElemStr: HVAndOffset => String = ???
}