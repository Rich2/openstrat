/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, pParse._

/** Sequence-defined efficient final classes backed by Arrays, ArrayBuffers etc. Includes actual sequences both mutable and immutable as well as classes such as
 * polygons and line paths that are defined by a sequence of data elements. So for example a Polygon in the Geom module is defined by a sequence of points, but
 * is a different type to the [[Pt2Arr]] class which is the immutable sequence class for 2-dimensional points. includes expandable buffers. */
trait SeqLike[+A] extends Any
{ /** Gives the final type of this class. */
  type ThisT <: SeqLike[A]

  /** Performs a side effecting function on each element of the specifying sequence in order. */
  def foreach[U](f: A => U): Unit

  /** Accesses the specifying sequence element by a 0 based index. For [[Sequ]]s this will an alternative name for apply. */
  @inline def elem(index: Int): A

  /** The number of data elements in the defining sequence. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying Array
   * will be a multiple of this number. For [[Sequ]]s this will be an alternative name for length. */
  def numElems: Int

  /** Sets / mutates an element in the Arr at the given index. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  def setElemUnsafe(index: Int, newElem: A @uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def setElemsUnsafe(index: Int, elems: A @uncheckedVariance *): Unit = elems.iForeach(index) { (i, a) => setElemUnsafe(i, a) }

  /** Mutates an element in the Arr at the given index. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  def mutateElemUnsafe(index: Int, f: A => A @uncheckedVariance): Unit = ???

  def fElemStr: A@uncheckedVariance => String = _.toString

  /** String specifying the type of this object. */
  def typeStr: String

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  def elemsStr: String

  override def toString: String = typeStr + elemsStr
}

object SeqLike
{
  /** Implicit method for creating [[SeqLike]] instances. */
  implicit def unshowEv[A, AA <: SeqLike[A]](implicit evIn: Unshow[A], buildIn: BuilderSeqLikeMap[A, AA]): Unshow[AA] = new Unshow[AA]
  { val evA: Unshow[A] = evIn
    val build: BuilderSeqLikeMap[A, AA] = buildIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare

    override def fromExpr(expr: Expr): ExcMon[AA] = expr match
    { case _: EmptyExprToken => Succ(build.uninitialised(0))
      
      case AlphaBracketExpr(id1, RArr1(BracketedStructure(sts, brs, _, _))) if (id1.srcStr == "Seq") && brs == Parentheses =>
        sts.mapErrBi(build)(s => evA.fromExpr(s.expr))
        
      case AlphaSquareParenth("Seq", _, sts) => sts.mapErrBi(build)(s => evA.fromExpr(s.expr))
      case AlphaParenth("Seq", sts) => sts.mapErrBi(build)(s => evA.fromExpr(s.expr))
      case e => expr.failExc(expr.toString + " unknown Expression for Seq")
    }
  }
}

/** Immutable [[SeqLike]] Common trait for the immutable [[Arr]] and [[SeqSpec]] classes, but excludes the mutable [[ArraryBuffer]] based [[Buff]] classes. */
trait SeqLikeImut[+A] extends Any, SeqLike[A]