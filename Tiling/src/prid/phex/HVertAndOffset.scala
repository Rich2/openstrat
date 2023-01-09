/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HVert]] direction of offset towards [[HCen]]. */
sealed trait HVDirn
{ /** The delta in R to the [[HCen]] from an [[HCoord]]. */
  def deltaR: Int

  /** The delta in C to the [[HCen]] from an [[HCoord]]. */
  def deltaC: Int
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
{ def deltaR: Int = 0
  def deltaC: Int = 0
}

/** Hex Vert Up offset. */
object HVUp extends HVDirn
{ def deltaR: Int = 1
  def deltaC: Int = 0
}

/** Hex Vert Up Right offset. */
object HVUR extends HVDirn
{ def deltaR: Int = 1
  def deltaC: Int = 1
}

object HVDR extends HVDirn
{ def deltaR: Int = -1
  def deltaC: Int = 1
}

object HVDn extends HVDirn
{ def deltaR: Int = -1
  def deltaC: Int = 0
}

object HVDL extends HVDirn
{ def deltaR: Int = -1
  def deltaC: Int = -1
}

object HVUL extends HVDirn
{ def deltaR: Int = 1
  def deltaC: Int = -1
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
}

object HVertAndOffset
{
  def apply(r: Int, c: Int, hvDirn: HVDirn): HVertAndOffset = ???
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