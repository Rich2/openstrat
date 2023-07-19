/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._

/** Efficient immutable Array based collection for [[Char]]s. When parsing sequences of [[Char]]s, it is recommended to use this class in conjunction
 * with the [[CharsOff]], the Char Arr offset class, which allows the dropping of [[Char]] elements without having to rebuild a new Array. */
final class CharArr(val unsafeArray: Array[Char]) extends AnyVal with ArrNoParam[Char]
{ type ThisT = CharArr

  /** Copy's the backing Array[[Char]] to a new Array[char]. End users should rarely have to use this method */
  def unsafeArrayCopy(operand: Array[Char], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Chars"
  override def unsafeSameSize(length: Int): CharArr = new CharArr(new Array[Char](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Char = unsafeArray(index)
  override def setElemUnsafe(i: Int, newElem: Char): Unit = unsafeArray(i) = newElem
  override def fElemStr: Char => String = _.toString

  /** append. Appends operand [[Char]] to this [[CharArr]]. */
  @targetName("append") override def +%(operand: Char): CharArr =
  { val newArray = new Array[Char](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = operand
    new CharArr(newArray)
  }

  @targetName("appendArr") override def ++(op: CharArr): CharArr =
  { val newArray = new Array[Char](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new CharArr(newArray)
  }

  override def reverse: CharArr =
  { val newArray = new Array[Char](length)
    iUntilForeach(0, length){ i => newArray(i) = unsafeArray(length - 1 - i) }
    new CharArr(newArray)
  }



  override def drop(n: Int): CharArr =
  { val nn = n.max0
    val newArray = new Array[Char]((length - nn).max0)
    iUntilForeach(length){ i => newArray(i) = unsafeArray(i + nn) }
    new CharArr(newArray)
  }

  @inline def offsetter(i: Int): CharsOff = new CharsOff(i)
  @inline def offsetter0: CharsOff = new CharsOff(0)
  @inline def offsetter1: CharsOff = new CharsOff(1)
  @inline def offsetter2: CharsOff = new CharsOff(2)
  @inline def offsetter3: CharsOff = new CharsOff(3)
  @inline def mkString: String = unsafeArray.mkString
}

/** Companion object of Chars class contains repeat parameter apply factor method. */
object CharArr
{ /** Repeat parameter apply factor method. */
  def apply(input: Char*): CharArr = new CharArr(input.toArray)
}

/** Immutable heapless iterator for Char arrays. At runtime this should just be an integer which indexes into a [[CharArr]]. This it self is just a
 * compile time wrapped [[Array]][Char]. This allows you to decompose the Char Array without having to create a new Array each time you drop a
 * character or characters.  */
class CharsOff(val offset0: Int) extends AnyVal with ArrBaseOff[Char, CharArr]
{ /** Index's into the backing Array, which is passed by an implicit parameter. */
  override def apply(index: Int)(implicit chars: CharArr): Char = chars(offset0 + index)

  def str: String = "CharsOff" + offset0.toString.enParenth
  override def toString = str
  def drop(n: Int): CharsOff = new CharsOff(offset0 + n)
  def drop1: CharsOff = new CharsOff(offset1)
  def drop2: CharsOff = new CharsOff(offset2)
  def drop3: CharsOff = new CharsOff(offset3)
  def drop4: CharsOff = new CharsOff(offset4)
  def length(implicit chars: CharArr): Int = chars.length - offset0
  def span(p: Char => Boolean)(implicit array: CharArr): (CharArr, CharsOff) =
  {
    var count = 0
    var continue = true
    while (offset0 + count < array.length & continue)
    {
      if (p(array(offset0 + count))) count += 1
      else continue = false
    }
    val newArray: Array[Char] = new Array[Char](count)
    iUntilForeach(count){i =>
      newArray(i) = array(offset0 + i)}
    (new CharArr(newArray), drop(count))
  }
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: Char => Boolean)(implicit chars: CharArr) : Boolean = (chars.length > offset0) &
    f(chars(offset0))
}

object CharsOff
{
  def unapply(inp: CharsOff): Option[Int] = inp match
  { case _ => Some(inp.offset0)
  }
}

/** Extractor for empty immutable heapless iterator for Chars. */
case object CharsOff0 { def unapply(inp: CharsOff)(implicit chars: CharArr): Boolean = chars.length - inp.offset0 <= 0 }

/** Extractor object for immutable heapless iterator for Chars with length == 1. */
object CharsOff1
{ /** Extractor for immutable heapless iterator for Chars with length == 1. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[Char] = ife(chars.length - inp.offset0 == 1, Some(chars(inp.offset0)), None)
}

/** Extractor object for immutable heapless iterator for Chars with length == 2. */
object CharsOff2
{ /** Extractor for immutable heapless iterator for Chars with length == 2. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char)] =
    ife(chars.length - inp.offset0 == 2, Some((chars(inp.offset0), chars(inp.offset1))), None)
}

/** Extractor object for immutable heapless iterator for Chars with length == 3. */
object CharsOff3
{ /** Extractor for immutable heapless iterator for Chars with length == 3. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char)] =
    ife(chars.length - inp.offset0 == 3, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2))), None)
}

/** Extractor object for immutable heapless iterator for Chars with length == 4. */
object CharsOff4
{ /** Extractor for immutable heapless iterator for Chars with length == 4. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char, Char)] =
    ife(chars.length - inp.offset0 == 4, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2), chars(inp.offset3))), None)
}

/** Extractor object for the first element for immutable heapless iterator for Chars with at length >= 1. Use this when you don't care about the
 *  tail. */
object CharsOffHead
{ /** Extractor for the first element, for immutable heapless iterator for Chars with length >= 1. Use this when you don't care about the tail. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[Char] =
  ife(chars.length - inp.offset0 >= 1, Some(chars(inp.offset0)), None)
}

/** Extractor object for the first 2 elements for immutable heapless iterator for Chars with length >= 2. Use this when you don't care about the
 *  tail. */
object CharsOffHead2
{ /** Extractor for the first 2 elements only for immutable heapless iterator for Chars with at least 2 element. Use this when you don't care about
    * the tail. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char)] =
    ife(chars.length - inp.offset0 >= 2, Some((chars(inp.offset0), chars(inp.offset1))), None)
}

/** Extractor object for the first 3 elements for immutable heapless iterator for Chars with length >= 3. Use this when you don't care about the
 *  tail. */
object CharsOffHead3
{ /** Extractor for the first 3 elements only for immutable heapless iterator for Chars with at least 3 element. Use this when you don't care about
 * the tail. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char)] =
    ife(chars.length - inp.offset0 >= 3, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2))), None)
}

/** Extractor object for the first 3 elements for immutable heapless iterator for Chars with length >= 3. Use this when you don't care about the
 *  tail. */
object CharsOffHead4
{ /** Extractor for the first 3 elements for immutable heapless iterator for Chars with length >= 3. Use this when you don't care about the tail */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char, Char)] =
    ife(chars.length - inp.offset0 >= 4, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2), chars(inp.offset3))), None)
}

/** Extractor for immutable heapless iterator for Chars with at l element. */
object CharsOff1Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 1 element. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, CharsOff)] =
  ife(chars.length - inp.offset0 >= 1, Some((chars(inp.offset0), inp.drop1)), None)
}

/** Extractor for immutable heapless iterator for Chars with at least 2 elements. */
object CharsOff2Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 2 elements. */
  def unapply(inp: CharsOff)(implicit array: CharArr): Option[(Char, Char, CharsOff)] =
    ife(array.length - inp.offset0 >= 2, Some((array(inp.offset0), (array(inp.offset1)), inp.drop2)), None)
}

/** Extractor for immutable heapless iterator for Chars with at least 3 elements. */
object CharsOff3Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 3 elements. */
  def unapply(inp: CharsOff)(implicit array: CharArr): Option[(Char, Char, Char, CharsOff)] =
    ife(array.length - inp.offset0 >= 3, Some((array(inp.offset0), array(inp.offset1), array(inp.offset2), inp.drop3)), None)
}

/** Extractor for immutable heapless iterator for Chars with at least 4 elements. */
object CharsOff4Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 4 elements. */
  def unapply(inp: CharsOff)(implicit array: CharArr): Option[(Char, Char, Char, Char, CharsOff)] =
  ife(array.length - inp.offset0 >= 4,
    Some((array(inp.offset0), array(inp.offset1), array(inp.offset2), array(inp.offset3), inp.drop4)),
    None)
}