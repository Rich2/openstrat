/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, pParse._

/** Sequence-defined efficient final classes backed by Arrays, ArrayBuffers etc. Includes actual sequences both mutable and immutable as well as
 *  classes such as polygons and line paths that are defined by a sequence of data elements. So for example a Polygon in the Geom module is defined by
 *  a sequence of points, but is a different type to the Pt2s class which is the immutable sequence class for 2 dimensional points. includes
 *  expandable buffers. */
trait SeqLike[+A] extends Any
{ /** Gives the final type of this class. */
  type ThisT <: SeqLike[A]

  /** Performs a side effecting function on each element of the specifying sequence in order. */
  def ssForeach[U](f: A => U): Unit

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  def setElemUnsafe(i: Int, newElem: A @uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def setElemsUnsafe(index: Int, elems: A @uncheckedVariance*): Unit = elems.iForeach(index) { (i, a) => setElemUnsafe(i, a) }

  def fElemStr: A@uncheckedVariance => String

  /** String specifying the type of this object. */
  def typeStr: String

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  def elemsStr: String

  override def toString: String = typeStr + elemsStr
}

object SeqLike
{
  /** Implicit method for creating List[A: Persist] instances. */
  implicit def unshowEv[A, AA <: SeqLike[A]](implicit evIn: Unshow[A], buildIn: SeqLikeMapBuilder[A, AA]): Unshow[AA] = new Unshow[AA] // with ShowIterable[A, List[A]]
  {
    val evA: Unshow[A] = evIn
    val build: SeqLikeMapBuilder[A, AA] = buildIn

    override def typeStr: String = "Seq" + evA.typeStr.enSquare

    override def fromExpr(expr: Expr): EMon[AA] = expr match {
      case eet: EmptyExprToken => Good(build.uninitialised(0))
      case AlphaBracketExpr(id1, RArr1(BracketedStatements(sts, brs, _, _))) if (id1.srcStr == "Seq") && brs == Parenthesis =>
        sts.eMapLike(s => evA.fromExpr(s.expr))(build)//.map(_.toList)
      case AlphaSquareParenth("Seq", ts, sts) => sts.eMapLike(s => evA.fromExpr(s.expr))(build)//.map(_.toList)
      case AlphaParenth("Seq", sts) => sts.eMapLike(s => evA.fromExpr(s.expr))(build)//.map(_.toList)
      case e => bad1(expr, expr.toString + " unknown Expression for Seq")
    }
  }
}

/** Base trait for all specialist Array buffer classes. Note there is no growArr methods on Buff. These methods are placed in the builders inheriting
 *  from [[SeqLikeCommonBuilder]]. */
trait Buff[A] extends Any with Sequ[A]
{ def grow(newElem: A): Unit
}