/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, collection.mutable.ArrayBuffer, reflect.ClassTag

trait ErrBiAccBase[+E <: Throwable, +A]
{
  /** The number of accumulated errors. */
  def errNum: Int

  /** The number of accumulated successes. */
  def succNum: Int

  /** Have there been no errors. */
  def errorFree: Boolean = errNum == 0
}

/** immutable class for accumulated [[ErrBi]], biased bifunctor for errors. */
class ErrBiAcc[+E <: Throwable, +A](val errsArray: Array[E] @uncheckedVariance, val succsArray: Array[A] @uncheckedVariance) extends ErrBiAccBase[E, A]
{ /** The accumulated errors. */
  def errs: RArr[E] = new RArr(errsArray)

  /** The accumulated successes. */
  def succs: RArr[A] = new RArr(succsArray)

  override def errNum: Int = errsArray.length
  override def succNum: Int = succsArray.length

  override def toString: String = s"$succNum successes, $errNum failures."
}

object ErrBiAcc
{
  def apply[E <: Throwable, A](input: ErrBi[E, A]*)(implicit ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[A] @uncheckedVariance): ErrBiAcc[E, A] =
    ErrBiAccBuff.fromSeq(input).unbuff
}

/** immutable class for accumulating [[ErrBi]]s, biased bifunctors for errors. */
class ErrBiAccBuff[+E <: Throwable, +A](val errs: ArrayBuffer[E] @uncheckedVariance, val succs: ArrayBuffer[A] @uncheckedVariance) extends ErrBiAccBase[E, A]
{
  def append(newElem: ErrBi[E, A] @uncheckedVariance): Unit = newElem.forFld(errs.append(_), succs.append(_))

  def unbuff(implicit ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[A] @uncheckedVariance): ErrBiAcc[E, A] = new ErrBiAcc(errs.toArray, succs.toArray)

  override def errNum: Int = errs.length
  override def succNum: Int = succs.length
}

object ErrBiAccBuff
{
  def apply[E <: Throwable, A](input: ErrBi[E, A]*): ErrBiAccBuff[E, A] = fromSeq(input)

  def fromSeq[E <: Throwable, A](input: Seq[ErrBi[E, A]]) =
  { val res = new ErrBiAccBuff[E, A](new ArrayBuffer[E]() , new ArrayBuffer[A]())
    input.foreach(res.append(_))
    res
  }
}