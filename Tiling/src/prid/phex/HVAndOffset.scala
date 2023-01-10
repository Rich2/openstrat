/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** An [[HVert]] and an offset. The Offset of from the [[HVert]] measured in an offset towards a neighbouring [[HCen]] or [[HVert]]. */
class HVAndOffset(val r: Int, val c: Int, val hvDirnInt: Int, val offset: Int)
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

object HVAndOffset
{
  def apply(hVert: HVert, hvDirn: HVDirn, offset: Int): HVAndOffset = apply(hVert.r, hVert.c, hvDirn, offset)

  def apply(r: Int, c: Int, hvDirn: HVDirn, offset: Int): HVAndOffset =
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
    new HVAndOffset(r, c, hvDirn.intValue, offset3)
  }

  def none(r: Int, c: Int) = new HVAndOffset(r, c, 0, 0)
  def none(hVert: HVert) = new HVAndOffset(hVert.r, hVert.c, 0, 0)
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
class HVAndOffsetPolygon extends PolygonLike[HVAndOffset]
{ override type ThisT = HVAndOffsetPolygon
  override type SideT = LineSegHVAndOffset

  /** The number of vertices and also the number of sides in this Polygon. */
  override def vertsNum: Int = ???

  /** Performs the side effecting function on the value of each vertex. */
  override def vertsForeach[U](f: HVAndOffset => U): Unit = ???

  /** Index with foreach on each vertx. Applies the side effecting function on the index with the value of each vertex. Note the function signature
   * follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element)
   * => B signature. */
  override def vertsIForeach[U](f: (Int, HVAndOffset) => Any): Unit = ???

  /** Maps the vertices of this polygon to an immutable Array like sequence of type B.
   *
   * @tparam B    The element type of the returned sequence.
   * @tparam ArrB The type of the immutable Array like sequence of B.
   * @return the immutable sequence collection by applying the supplied function to each vertex. */
  override def vertsMap[B, ArrB <: Arr[B]](f: HVAndOffset => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = ???

  /** Folds over the vertices.
   *
   * @tparam B type of the accumulator return value of this method. */
override def vertsFold[B](init: B)(f: (B, HVAndOffset) => B): B = ???

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
override def vert(index: Int): HVAndOffset = ???

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
override def vertsPrevForEach[U](f: (HVAndOffset, HVAndOffset) => U): Unit = ???
override def sidesForeach[U](f: LineSegHVAndOffset => U): Unit = ???

  /** Accesses the specifying sequence element by a 0 based index. */
override def ssIndex(index: Int): HVAndOffset = ???

  /** The number of data elements in the defining sequence. These collections use underlying mutable Arrays and ArrayBuffers. The length of the
   * underlying Array will be a multiple of this number. */
override def ssLength: Int = ???

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
override def unsafeSetElem(i: Int, newElem: HVAndOffset): Unit = ???
override def fElemStr: HVAndOffset => String = ???

  /** String specifying the type of this object. */
override def typeStr: String = ???
}