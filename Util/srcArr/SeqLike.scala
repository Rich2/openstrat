/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, pParse._

/** Common trait for immutable [[Sequ]]s, mutable [[Buff]]s and [[SeqSpec]] classes that are not sequences but can be specified by a sequence, for example a
 *  sequence of points can specify a polygon. Designed as a compromise between type safety and effciency. Using backing [[Array]]s for use facing types, but
 *  using backing [[ArrayBuffer]]s behind the scenes keeping mutation encapsulated. Many methods and properties are common to all three [[Sequ]]s, [[Buff]]s and
 *  [[SeqSpec]]s. */
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

  def fElemStr: A@uncheckedVariance => String = _.toString

  /** String specifying the type of this object. */
  def typeStr: String

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  def elemsStr: String

  override def toString: String = typeStr + elemsStr
}

object SeqLike
{ /** Implicit method for creating [[SeqLike]] instances. */
  given unshowEv[A, AA <: SeqLike[A]](using evA: Unshow[A], build: BuilderMapSeqLike[A, AA]): Unshow[AA] = new Unshow[AA]
  { override def typeStr: String = "Seq" + evA.typeStr.enSquare

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

/** A [[SeqLike]] class that is backed by an [[Array]] or an [[ArrayBuffer]] which can be mutated. This mutation will mostly be used by builders rather than end
 * users. There are other cases where it can be useful, for example in simultaneous game turn resolution. */
trait SeqLikeBacked[+A] extends Any, SeqLike[A]
{ /** Sets / mutates an element in the Arr at the given index. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  def setElemUnsafe(index: Int, newElem: A @uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def setElemsUnsafe(index: Int, elems: A @uncheckedVariance*): Unit = elems.iForeach(index) { (i, a) => setElemUnsafe(i, a) }
  
  /** Mutates an element in the Arr at the given index. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  def mutateElemUnsafe(index: Int, f: A => A @uncheckedVariance): Unit = setElemsUnsafe(index, f(elem(index)))
}