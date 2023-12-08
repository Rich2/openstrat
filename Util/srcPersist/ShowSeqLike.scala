/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Base traits for all [[Show]] type classs instances for sequence like objectss including not only [[SeqLike]]s but also standard library
 *  collections like [[Iterable]] and [[Array]]s. */
trait ShowSeqLike[Ae, A] extends ShowCompound[A]
{ def evA: Show[Ae]

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
    showForeach(obj, a => acc = acc.max(evA.syntaxDepth(a) + 1))
    acc
  }

  final override def show(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  { val depth = syntaxDepth(obj)
    way match
    { case ShowCommas if depth <= 2 => showMap(obj)(el => evA.show(el, ShowStd, maxPlaces, minPlaces)).mkCommaSpaceSpecial
      case ShowSemis if depth <= 3 => showMap(obj)(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiSpaceSpecial
      case ShowTyped => typeStr + evA.typeStr.enSquare + showMap(obj)(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
      case _ => typeStr + showMap(obj)(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiSpaceSpecial.enParenth
    }
  }
}

object ShowSeqLike
{
  def apply[Ae, A](typeStr: String, fForeach: (A, Ae => Unit) => Unit)(implicit evA: Show[Ae]): ShowSeqLike[Ae, A] =
    new ShowSeqLikeImp[Ae, A](typeStr, fForeach)(evA)

  class ShowSeqLikeImp[Ae, A](val typeStr: String, fForeach: (A, Ae => Unit) => Unit)(implicit val evA: Show[Ae]) extends ShowSeqLike[Ae, A]
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

/** [[Show]] type class for showing [[Sequ]][Ae] objects. */
trait ShowSeqSpec[Ae, A <: SeqSpec[Ae]] extends ShowSeq[Ae, A]
{ override def showForeach(obj: A, f: Ae => Unit): Unit = obj.ssForeach(f)
  override def toString: String = "Show" + typeStr
}

object ShowSeqSpec
{ /** Factory apply method for constructing [[ShowSeqSpec]] type class instances. */
  def apply[Ae, A <: SeqSpec[Ae]](typeStrIn: String)(implicit evAIn: Show[Ae]): ShowSeqSpec[Ae, A] = new ShowSeqSpec[Ae, A]
  { override val typeStr: String = typeStrIn
    override val evA: Show[Ae] = evAIn
  }
}