/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, unchecked.uncheckedVariance, collection.mutable.ArrayBuffer, reflect.ClassTag

/** This should replace [[ErrbiAcc]] */
trait Results[+E, +A]
{ /** Backing [[Array]] for fails. */  
  def failsArray: Array[E] @uncheckedVariance

  /** Backing [[Array]] for successes. */
  def succsArray: Array[A] @uncheckedVariance
  
  /** The fails of these results. */
  final def fails: RArr[E] = new RArr(failsArray)

  /** The successes of these results. */
  final def succs: RArr[A] = new RArr(succsArray)
  
  /** Child result members of this [[Results]] class. */
  def subs: RArr[Results[E, A]]
  
  /** the number of total fails including in child objects in these results. */
  def numFails: Int = subs.foldLeft(failsArray.length)(_ + _.numFails)

  /** the number of total successes including in child objects in these results. */
  def numSuccs: Int = subs.foldLeft(succsArray.length)(_ + _.numSuccs)
  
  /** Shortest summary of these results. */
  def reportStr: String = s"$numSuccs successes, $numFails fails"
}

object Results
{ /** Factory apply method to create a [[Results]] class of the most precise type. */
  def apply[E, A, R <: Results[E, A]](input: Either[E, A]*)(using build: ResultsBuilder[E, A, R]): R = build.fromIterable(input)

  /** Implementation class for the General case of the [[Results]] class. */
  case class ResultsGen[+E, +A](failsArray: Array[E] @uncheckedVariance, succsArray: Array[A] @uncheckedVariance,
    subsArr: Array[Results[E, A]] @uncheckedVariance) extends Results[E, A]
  { override def subs: RArr[Results[E, A]] = new RArr(subsArr)
  }
}

/** Type class builder for constructing [[Results]] classes. */
abstract class ResultsBuilder[E, A, R <: Results[E, A]](using classTagE: ClassTag[E], classTagA: ClassTag[A], classTagR: ClassTag[R])
{ /** Constructs the given [[Results]] class from an [[Array]] of errors and an [[Array]] of successes. */
  def fromArrays(arrayE: Array[E], arrayA: Array[A], arrayR: Array[R]): R

  /** Constructs the given [[Results]] class from an [[Iterable]] of [[Either]]s. */
  def fromIterable(seq: Iterable[Either[E, A]]): R =
  { val eBuff: ArrayBuffer[E] = new ArrayBuffer[E]()
    val aBuff: ArrayBuffer[A] = new ArrayBuffer[A]()
    seq.foreach(_.fold(eBuff.append(_), aBuff.append(_)))
    fromArrays(eBuff.toArray, aBuff.toArray, Array[R]())
  }

  /** Constructs the given [[Results]] class from a [[Sequ]] of [[Either]]s. */
  def fromSequ(seq: Sequ[Either[E, A]]): R =
  { val eBuff: ArrayBuffer[E] = new ArrayBuffer[E]()
    val aBuff: ArrayBuffer[A] = new ArrayBuffer[A]()
    seq.foreach(_.fold(eBuff.append(_), aBuff.append(_)))
    fromArrays(eBuff.toArray, aBuff.toArray, Array[R]())
  }
  
  def compound(arr: RArr[R]): R = fromArrays(Array(), Array(), arr.arrayUnsafe)
}

object ResultsBuilder
{
  given builderGen[E, A](using ctE: ClassTag[E], ctA: ClassTag[A]): ResultsBuilder[E, A, Results[E, A]] = new ResultsBuilder[E, A, Results[E, A]]
  { 
    override def fromArrays(arrayE: Array[E], arrayA: Array[A], arrayR: Array[Results[E, A]]): Results[E, A] =
      new Results.ResultsGen[E, A](arrayE, arrayA, arrayR)
  }
}

/** Trait for the accumulation of successes and errors for operations with [[Either]] return types. */
trait ErrBiAccBase[+E, +B]
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

/** immutable class for the accumulation of successes and errors for operations with [[Either]] return types where the error type inherits from [[Throwable]]. */
class ErrBiAcc[+E, +B](val errsArray: Array[E] @uncheckedVariance, val succsArray: Array[B] @uncheckedVariance) extends ErrBiAccBase[E, B]
{ /** The accumulated errors. */
  def errs: RArr[E] = new RArr(errsArray)

  /** The accumulated successes. */
  def succs: RArr[B] = new RArr(succsArray)

  override def errNum: Int = errsArray.length
  override def succNum: Int = succsArray.length
  override def toString: String = s"$succNum successes, $errNum failures."
  def summaryLine(using evST: ShowType[B]): String = s"$succNum ${evST.typeStr} successes, $errNum failures."
  def errsSummary(using evST: ShowType[B]): String = errsArray.foldLeft(summaryLine)(_ --- _.toString)

  def msgErrsSummary(succMsg: String)(using evST: ShowType[B]): String =
  { val sumLine = evST match
    { case sde: ShowEffectReport[_] => s"$succNum ${sde.actionStr(succNum) } $succMsg, $errNum failures."
      case _ => s"$succNum ${evST.typeStr} successes $succMsg, $errNum failures."
    }
    errsArray.foldLeft(sumLine)(_ --- _.toString)
  }

  def msg2ErrsSummary(succMsg1: String, succMsg2: String)(using evST: ShowType[B]): String =
  { val sumLine: String = evST match
  { case sde: ShowEffectReport[_] => s"$succNum $succMsg1 ${sde.actionStr(succNum) } $succMsg2, $errNum failures."
    case _ => s"$succNum $succMsg1 ${evST.typeStr} successes $succMsg2, $errNum failures."
  }
    errsArray.foldLeft(sumLine)(_ --- _.toString)
  }

  override def errHead: E = errsArray(0)
  override def errsforeach(f: E => Unit): Unit = errsArray.foreach(f)

  /** Appends [[Either]] element to this accumulator widening the type of the error and the successful value if necessary. Order of successes and fails is
   * preserved but not the overall order. */
  @targetName("append") @inline def ++[EE >: E <: Throwable, BB >: B](operand: ErrBiAcc[EE, BB] @uncheckedVariance)(using ctE: ClassTag[EE] @uncheckedVariance,
    ctB: ClassTag[BB] @uncheckedVariance): ErrBiAcc[EE, BB] = new ErrBiAcc[EE, BB](errsArray ++ operand.errsArray, succsArray ++ operand.succsArray)

  /** Appends [[ErrBiAcc]] to this accumulator widening the type of the error and the successful value if necessary. Order of successes and fails is preserved
   * but not the overall order. */
  @targetName("appendElem") @inline def +%[EE >: E <: Throwable, BB >: B](newElem: Either[EE, BB] @uncheckedVariance)(using ctE: ClassTag[EE] @uncheckedVariance,
    ctB: ClassTag[BB] @uncheckedVariance): ErrBiAcc[EE, BB] =
    newElem.fold(err => new ErrBiAcc[EE, BB](errsArray :+ err, succsArray.asInstanceOf[Array[BB]]), b => new ErrBiAcc(errsArray, succsArray :+ b))
}

object ErrBiAcc
{ /** Factory apply method to construct an [[ErrBiAcc]]. */
  def apply[E <: Throwable, B](input: Either[E, B]*)(using ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] =
    ErrBiAccBuff.fromSeq(input).unbuff

  /** Constructs an [[ErrBiAcc]] from a single error. */
  def err1[E <: Throwable, B](err1: E)(using ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] =
    new ErrBiAcc[E, B](Array[E](err1), Array[B]())

    /** Constructs an [[ErrBiAcc]] from two errors. */
  def errs2[E <: Throwable, B](err1: E, err2: E)(using ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] =
    new ErrBiAcc[E, B](Array[E](err1, err2), Array[B]())
}

/** immutable class for accumulating [[Either]]s, biased bifunctors for errors. */
class ErrBiAccBuff[+E, +B](val errs: ArrayBuffer[E] @uncheckedVariance, val succs: ArrayBuffer[B] @uncheckedVariance) extends ErrBiAccBase[E, B]
{ /** Appends an element to this buffer. */
  def grow(newElem: Either[E, B] @uncheckedVariance): Unit = newElem.fold(errs.append(_), succs.append(_))

  /** Appends elements to this buffer. */
  def growAcc(newElems: ErrBiAcc[E, B] @uncheckedVariance): Unit =
  { newElems.errs.foreach(errs.append(_))
    newElems.succs.foreach(succs.append(_))
  }

  /** Converts from a buffer to an immutable [[ErrBiAcc]]. */
  def unbuff(using ctE: ClassTag[E] @uncheckedVariance, ctA: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] = new ErrBiAcc(errs.toArray, succs.toArray)

  override def errNum: Int = errs.length
  override def succNum: Int = succs.length
  override def errHead: E = errs(0)
  override def errsforeach(f: E => Unit): Unit = errs.foreach(f)
}

object ErrBiAccBuff
{ /** Factory apply method to construct an [[ErrBiAccBuff]]. */
  def apply[E <: Throwable, B](input: Either[E, B]*): ErrBiAccBuff[E, B] = fromSeq(input)

  /** Utility method used by the [[ErrBiAcc]] companion object factory apply method. */
  def fromSeq[E <: Throwable, B](input: Seq[Either[E, B]]): ErrBiAccBuff[E, B] =
  { val res = new ErrBiAccBuff[E, B](new ArrayBuffer[E]() , new ArrayBuffer[B]())
    input.foreach(res.grow(_))
    res
  }
}

/** type class to provide summary [[String]]s for [[ErrBiAccBase]] objects. */
trait ErrBiSummary[+E <: Throwable, +B]
{ /** Provides the numerical part pf the summary [[String]]. */
  def endStr(eba: ErrBiAccBase[E @uncheckedVariance, B @uncheckedVariance]): String

  /** A single line summary [[String]] for [[ErrBiAccBase]] objects. */
  def summaryStr(leadStr: String, eba: ErrBiAccBase[E @uncheckedVariance, B @uncheckedVariance]): String =
    leadStr + ": " + ife(eba.errNum == 1 && eba.succNum == 0, eba.errHead, endStr(eba))
}