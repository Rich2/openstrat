/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, collection.mutable.ArrayBuffer, reflect.ClassTag

/** Trait for the accumulation of successes and errors. */
trait ErrBiAccBase[+E <: Throwable, +B]
{ /** The number of accumulated errors. */
  def errNum: Int

  /** The number of accumulated successes. */
  def succNum: Int

  /** Have there been no errors. */
  def errorFree: Boolean = errNum == 0

  /** The first error may throw exception if no errors. */
  def errHead: E

  /** Foreachs over each error. */
  def errsforeach(f: E => Unit): Unit

  /** Prints out each error to the console, on its own line */
  def errsPrint: Unit = errsforeach(println(_))
}

object ErrBiAccBase
{ /** Extension class for [[ErrBiAccBase]]. */
  implicit class errBiAccBaseExtensions[+E <: Throwable, +B](thisEBAB: ErrBiAccBase[E, B])
  { /** Extension method to produce a summary line of the successes and failures of this [[ErrBiAccBase]]. */
    def summaryStr(leadStr: String)(implicit ev: ErrBiSummary[E, B] @uncheckedVariance): String = ev.summaryStr(leadStr, thisEBAB)
  }
}

/** immutable class for accumulated [[ErrBi]], biased bifunctor for errors. */
class ErrBiAcc[+E <: Throwable, +B](val errsArray: Array[E] @uncheckedVariance, val succsArray: Array[B] @uncheckedVariance) extends ErrBiAccBase[E, B]
{ /** The accumulated errors. */
  def errs: RArr[E] = new RArr(errsArray)

  /** The accumulated successes. */
  def succs: RArr[B] = new RArr(succsArray)

  override def errNum: Int = errsArray.length
  override def succNum: Int = succsArray.length
  override def toString: String = s"$succNum successes, $errNum failures."
  override def errHead: E = errsArray(0)
  override def errsforeach(f: E => Unit): Unit = errsArray.foreach(f)
}

object ErrBiAcc
{ /** Factory apply method to construct an [[ErrBiAcc]]. */
  def apply[E <: Throwable, B](input: ErrBi[E, B]*)(implicit ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] =
    ErrBiAccBuff.fromSeq(input).unbuff
}

/** immutable class for accumulating [[ErrBi]]s, biased bifunctors for errors. */
class ErrBiAccBuff[+E <: Throwable, +B](val errs: ArrayBuffer[E] @uncheckedVariance, val succs: ArrayBuffer[B] @uncheckedVariance) extends ErrBiAccBase[E, B]
{ /** Appends an element to this buffer. */
  def grow(newElem: ErrBi[E, B] @uncheckedVariance): Unit = newElem.forFld(errs.append(_), succs.append(_))

  /** Appends elements to this buffer. */
  def growAcc(newElems: ErrBiAcc[E, B] @uncheckedVariance): Unit = {newElems.errs.foreach(errs.append(_)); newElems.succs.foreach(succs.append(_)) }

  /** Converts from a buffer to an immutable [[ErrBiAcc]]. */
  def unbuff(implicit ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] = new ErrBiAcc(errs.toArray, succs.toArray)

  override def errNum: Int = errs.length
  override def succNum: Int = succs.length
  override def errHead: E = errs(0)
  override def errsforeach(f: E => Unit): Unit = errs.foreach(f)
}

object ErrBiAccBuff
{ /** Factory apply method to construct an [[ErrBiAccBuff]]. */
  def apply[E <: Throwable, B](input: ErrBi[E, B]*): ErrBiAccBuff[E, B] = fromSeq(input)

  /** Utility method used by the [[ErrBiAcc]] companion object factory apply method. */
  def fromSeq[E <: Throwable, B](input: Seq[ErrBi[E, B]]): ErrBiAccBuff[E, B] =
  { val res = new ErrBiAccBuff[E, B](new ArrayBuffer[E]() , new ArrayBuffer[B]())
    input.foreach(res.grow(_))
    res
  }
}

/** type class to proived summary [[String]]s for [[ErrBiAccBase]] objects. */
trait ErrBiSummary[+E <: Throwable, +B]
{ /** Provides the nummerical part pf the summary [[String]]. */
  def endStr(eba: ErrBiAccBase[E @uncheckedVariance, B @uncheckedVariance]): String

  /** A single line summary [[String]] for [[ErrBiAccBase]] objects. */
  def summaryStr(leadStr: String, eba: ErrBiAccBase[E @uncheckedVariance, B @uncheckedVariance]): String =
    leadStr + ": " + ife(eba.errNum == 1 && eba.succNum == 0, eba.errHead, endStr(eba))
}