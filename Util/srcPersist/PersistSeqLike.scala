/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Base traits for all sequence like classes including not only [[SeqLike]]s but also standard library collections like [[Iterable]] and
 * [[Array]]. */
trait ShowSeqLike[A, R] extends ShowCompound[R]
{ def evA: Show[A]

  def showForeach(obj: R, f: A => Unit): Unit

  override def syntaxDepth(obj: R): Int =
  { var acc = 2
    showForeach(obj, a => acc = acc.max(evA.syntaxDepth(a) + 1))
    acc
  }

  final def showMap(obj: R)(f: A => String): StrArr =
  { val buffer: ArrayBuffer[String] = Buffer[String]()
    showForeach(obj, a => buffer.append(f(a)))
    new StrArr(buffer.toArray)
  }

  final override def showDec(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
  { val depth = syntaxDepth(obj)
    way match
    { case ShowCommas if depth <= 2 => showMap(obj)(el => evA.showDec(el, ShowStandard, maxPlaces, 0)).mkComma
      case ShowSemis if depth <= 3 => showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkSemi
      case ShowTyped => typeStr + evA.typeStr.enSquare + showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkSemiParenth
      case _ => typeStr + showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkSemiParenth
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

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSeqSpec[A, R <: SeqSpec[A]] extends ShowSeq[A, R]
{ override def showForeach(obj: R, f: A => Unit): Unit = obj.ssForeach(f)
}

object ShowSeqSpec
{
  def apply[A, R <: SeqSpec[A]](typeStrIn: String)(implicit evAIn: Show[A]): ShowSeqSpec[A, R] = new ShowSeqSpec[A, R]
  { override val typeStr: String = typeStrIn
    override val evA: Show[A] = evAIn
  }
}