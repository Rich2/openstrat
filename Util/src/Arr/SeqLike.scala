/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Sequence-defined efficient final classes backed by Arrays, ArrayBuffers etc. Includes actual sequences both mutable and immutable as well as
 *  classes such as polygons and line paths that are defined by a sequence of data elements. So for example a Polygon in the Geom module is defined by
 *  a sequence of points, but is a different type to the Pt2s class which is the immutable sequence class for 2 dimensional points. includes
 *  expandable buffers. */
trait SeqLike[+A] extends Any {
  /** Gives the final type of this class. */
  type ThisT <: SeqLike[A]

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqDef]] of the this [[SeqImut]]'s final type. */
  def unsafeSameSize(length: Int): ThisT

  /** The number of data elements in the defining sequence. These collections use underlying mutable Arrays and ArrayBuffers. The length of the
   * underlying Array will be a multiple of this number. */
  def sdLength: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def sdLengthStr: String = sdLength.toString

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  def unsafeSetElem(i: Int, value: A@uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def unsafeSetElems(index: Int, elems: A@uncheckedVariance*): Unit = elems.iForeach(index) { (i, a) => unsafeSetElem(i, a) }

  def fElemStr: A@uncheckedVariance => String

  /** String specifying the type of this object. */
  def typeStr: String

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  def elemsStr: String // = dataMap(fElemStr).mkString("; ").enParenth

  override def toString: String = typeStr + elemsStr

  /** Accesses the defining sequence element by a 0 based index. */
  @inline def sdIndex(index: Int): A

}

/** [[ShowT] type class for showing [[DataGen]][A] objects. */
class DataGenShowT[A, R <: SeqDef[A]](val evA: ShowT[A]) extends ShowTSeqLike[A, R]
{
  override def syntaxDepthT(obj: R): Int = obj.dataFold(1)((acc, a) => acc.max(evA.syntaxDepthT(a)))
  override def showDecT(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    typeStr + evA.typeStr.enSquare + obj.dataMap(a => evA.showDecT(a, style, maxPlaces, minPlaces))
}