/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Base traits for all sequence like classes including not only [[SeqLike]]s but also standard library collections like [[Iterable]] and
 * [[Array]]. */
trait ShowSeqLike[A, R] extends ShowCompound[R]
{ def evA: Show[A]

  def showForeach(obj: R, f: A => Unit): Unit

  final def showMap(obj: R)(f: A => String): StrArr = {
    val buffer: ArrayBuffer[String] = Buffer[String]()
    showForeach(obj, a => buffer.append(f(a)))
    new StrArr(buffer.toArray)
  }

  override def syntaxDepth(obj: R): Int = {
    var acc = 2
    showForeach(obj, a => acc = acc.max(evA.syntaxDepth(a) + 1))
    acc
  }

  final override def show(obj: R, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  { val depth = syntaxDepth(obj)
    way match
    { case ShowCommas if depth <= 2 => showMap(obj)(el => evA.show(el, ShowStd, maxPlaces, minPlaces)).mkComma
      case ShowSemis if depth <= 3 => showMap(obj)(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemi
      case ShowTyped => typeStr + evA.typeStr.enSquare + showMap(obj)(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
      case _ => typeStr + showMap(obj)(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
    }
  }
}

object ShowSeqLike
{
  def apply[A, R](typeStr: String, fForeach: (R, A => Unit) => Unit)(implicit evA: Show[A]): ShowSeqLike[A, R] =
    new ShowSeqLikeImp[A, R](typeStr, fForeach)(evA)

  class ShowSeqLikeImp[A, R](val typeStr: String, fForeach: (R, A => Unit) => Unit)(implicit val evA: Show[A]) extends ShowSeqLike[A, R]
  { override def showForeach(obj: R, f: A => Unit): Unit = fForeach(obj, f)
  }
}

trait TellSeqLike[A] extends Tell
{ /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  override def str: String = tell(ShowStd)
  def evA: Show[A]
  override def tellDepth: Int =
  { var acc = 2
    tellForeach(a => acc = acc.max(evA.syntaxDepth(a) + 1))
    acc
  }
  def tellForeach(f: A => Unit): Unit

  final def tellMap(f: A => String): StrArr =
  { val buffer: ArrayBuffer[String] = Buffer[String]()
    tellForeach(a => buffer.append(f(a)))
    new StrArr(buffer.toArray)
  }

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  override def tell(style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = style match {
    case ShowCommas if tellDepth <= 2 => tellMap(el => evA.show(el, ShowStd, maxPlaces, minPlaces)).mkComma
    case ShowSemis if tellDepth <= 3 => tellMap(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemi
    case ShowTyped => typeStr + evA.typeStr.enSquare + tellMap(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
    case _ => typeStr + tellMap(el => evA.show(el, ShowCommas, maxPlaces, minPlaces)).mkSemiParenth
  }

}

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSeqSpec[A, R <: SeqSpec[A]] extends ShowSeq[A, R]
{ override def showForeach(obj: R, f: A => Unit): Unit = obj.ssForeach(f)
  override def toString: String = "Show" + typeStr
}

object ShowSeqSpec
{
  def apply[A, R <: SeqSpec[A]](typeStrIn: String)(implicit evAIn: Show[A]): ShowSeqSpec[A, R] = new ShowSeqSpec[A, R]
  { override val typeStr: String = typeStrIn
    override val evA: Show[A] = evAIn
  }
}