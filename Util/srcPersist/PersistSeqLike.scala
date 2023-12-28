/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** Base traits for all [[Show]] type classs instances for sequence like objectss including not only [[SeqLike]]s but also standard library
 *  collections like [[Iterable]] and [[Array]]s. */
trait ShowSeqLike[Ae, A] extends ShowCompound[A]
{ def showAeEv: Show[Ae]

  /** Foreach's all the elements of the sequence like object that is being shown. */
  def showForeach(obj: A, f: Ae => Unit): Unit

  /** Maps over all the elements of the sequence like object that is being shown. */
  final def showMap(obj: A)(f: Ae => String): StrArr =
  { val buffer: ArrayBuffer[String] = Buffer[String]()
    showForeach(obj, a => buffer.append(f(a)))
    new StrArr(buffer.toArray)
  }

  override def syntaxDepth(obj: A): Int =
  { var acc = 2
    showForeach(obj, a => acc = acc.max(showAeEv.syntaxDepth(a) + 1))
    acc
  }

  final override def show(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  { val depth = syntaxDepth(obj)
    way match
    { case ShowCommas if depth <= 2 => showMap(obj)(el => showAeEv.show(el, ShowStd, maxPlaces, minPlaces)).mkCommaSpaceSpecial
      case ShowSemis if depth <= 3 => showMap(obj)(el => showAeEv.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiSpaceSpecial
      case ShowTyped => typeStr + showAeEv.typeStr.enSquare + showMap(obj)(el => showAeEv.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
      case _ => typeStr + showMap(obj)(el => showAeEv.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiSpaceSpecial.enParenth
    }
  }

  override def toString: String = "Show" + typeStr
}

object ShowSeqLike
{ /** Factory apply method for the prducing the general cases of [[ShowSeqLike]] type class instances / evidence. */
  def apply[Ae, A](typeStr: String, fForeach: (A, Ae => Unit) => Unit)(implicit evA: Show[Ae]): ShowSeqLike[Ae, A] =
    new ShowSeqLikeImp[Ae, A](typeStr, fForeach)(evA)

  /** Implementation class for the general case of [[ShowSeqLike]] type class instances. */
  class ShowSeqLikeImp[Ae, A](val typeStr: String, fForeach: (A, Ae => Unit) => Unit)(implicit val showAeEv: Show[Ae]) extends ShowSeqLike[Ae, A]
  { override def showForeach(obj: A, f: Ae => Unit): Unit = fForeach(obj, f)
  }
}

/** [[Tell]] trait for seequence like objects. The type parameter is named Ae, to correpond to the Ae type class in the corresponding [[Show]] and
 * [[Unshow]] type class instances for the type of this object. */
trait TellSeqLike[Ae] extends Tell
{ /** The most basic Show method, paralleling the show method on [[Show]] type class instances. */
  override def str: String = tell(ShowStdNoSpace)

  /** Show type class instance for the elements of this class. */
  def evA: Show[Ae]

  override def tellDepth: Int =
  { var acc = 2
    tellForeach(a => acc = acc.max(evA.syntaxDepth(a) + 1))
    acc
  }
  def tellForeach(f: Ae => Unit): Unit

  final def tellMap(f: Ae => String): StrArr =
  { val buffer: ArrayBuffer[String] = Buffer[String]()
    tellForeach(a => buffer.append(f(a)))
    new StrArr(buffer.toArray)
  }

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  override def tell(style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = style match {
    case ShowCommas if tellDepth <= 2 => tellMap(el => evA.show(el, ShowStdNoSpace, maxPlaces, minPlaces)).mkComma
    case ShowSemis if tellDepth <= 3 => tellMap(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemi
    case ShowTyped => typeStr + evA.typeStr.enSquare + tellMap(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
    case _ => typeStr + tellMap(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
  }

}

/** [[Unshow]] type classes for SeqLike. This trait actually implements fromExpr method.  */
trait UnshowSeqLike[Ae, A] extends Unshow[A]
{ /** [[Unshow]] type class instance for the elements of the seqLike. */
  def unshowAeEv: Unshow[Ae]
  def build: BuilderCollMap[Ae, A]

  override def fromExpr(expr: Expr): EMon[A] = expr match
  { case _: EmptyExprToken => Good(build.empty)

    case AlphaMaybeSquareParenth(str1, sts) if str1 == typeStr => if(unshowAeEv.useMultiple) Multiple.collFromArrStatement(sts)(unshowAeEv, build)
    else sts.mapEMon(build)(s => unshowAeEv.fromExpr(s.expr))

    case ExprSeqNonEmpty(mems) => if (unshowAeEv.useMultiple) Multiple.collFromArrExpr(mems)(unshowAeEv, build)
    else mems.mapEMon(build)(e => unshowAeEv.fromExpr(e))

    case e => bad1(expr, expr.toString + " unknown Expression for this sequence based class.")
  }
}

object UnshowSeqLike
{ /** Factory apply method for creating [[Unshow]] type class instances for [[SeqLike]] objects. */
  def apply[A, R](typeStr: String)(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSeqLike[A, R] =
    new UnshowSeqLikeImp[A, R](typeStr, evA, build)

  /** Implementation class for the general cases of [[UnshowSeqLike]]. Use [[UnshowSeq]] for any actual sequence classes. */
  class UnshowSeqLikeImp[A, R](val typeStr: String, val unshowAeEv: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
}

/** [[Unshow]] type class instances for sequences, both [[Sequ]] and standard library classes such as [[List]] and
 * [[Array]]. Uses the typeStr "Seq". As all these different types are persisted as logical sequences. Their in memory
 * storage structure is irrelevant. They can all be reconstructed / unshown from an RSON Seq. */
class UnshowSeq[A, R](val unshowAeEv: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
{ def typeStr: String = "Seq"
  override def useMultiple: Boolean = false
}

object UnshowSeq
{ /** Factory apply method for creating [[Unshow]] type class instances / evidence for any type of sequence. */
  def apply[A, R]()(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSeq[A, R] = new UnshowSeq[A, R](evA, build)
}

/** [[Unshow]] type class instances for building classes from sequences through two builders. */
class UnshowFromArr[Ae, ArrAe <: Arr[Ae], A](val typeStr: String, f: ArrAe => A)(implicit evA: Unshow[Ae],
  build1: BuilderArrMap[Ae, ArrAe]) extends Unshow[A]
{ /** [[Unshow]]s the sequence from which the actual wanted type is mapped. */
  val stage: UnshowSeqLike[Ae, ArrAe] = UnshowSeqLike[Ae, ArrAe](typeStr)(evA, build1)

  override def fromExpr(expr: Expr): EMon[A] = stage.fromExpr(expr).map(f)
}

object UnshowFromArr
{
  def apply[Ae, ArrAe <: Arr[Ae], A](typeStr: String, f: ArrAe => A)(implicit evA: Unshow[Ae], build1: BuilderArrMap[Ae, ArrAe]):
    UnshowFromArr[Ae, ArrAe, A] = new UnshowFromArr[Ae, ArrAe, A](typeStr, f)
}