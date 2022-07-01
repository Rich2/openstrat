/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Efficient immutable Array based collection for Chars. */
final class CharArr(val unsafeArray: Array[Char]) extends AnyVal with SeqImut[Char]
{ type ThisT = CharArr

  /** Copy's the backing Array[[Char]] to a new Array[char]. End users should rarely have to use this method */
  def unsafeArrayCopy(operand: Array[Char], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Chars"
  override def unsafeSameSize(length: Int): CharArr = new CharArr(new Array[Char](length))
  override def sdLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length
  override def sdIndex(index: Int): Char = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Char): Unit = unsafeArray(i) = value

  override def fElemStr: Char => String = _.toString

  /** Append another Chars collection. */
  def ++ (op: CharArr): CharArr =
  { val newArray = new Array[Char](sdLength + op.sdLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, sdLength)
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

/** Immutable heapless iterator for Char arrays. */
class CharsOff(val offset0: Int) extends AnyVal with ArrBaseOff[Char, CharArr]
{
  override def apply(index: Int)(implicit chars: CharArr): Char = chars(offset0 + index)
  def str: String = "CharsOff" + offset0.toString.enParenth
  override def toString = str
  def drop(n: Int): CharsOff = new CharsOff(offset0 + n)
  def drop1: CharsOff = new CharsOff(offset1)
  def drop2: CharsOff = new CharsOff(offset2)
  def drop3: CharsOff = new CharsOff(offset3)
  def drop4: CharsOff = new CharsOff(offset4)
  def length(implicit chars: CharArr): Int = chars.sdLength - offset0
  def span(p: Char => Boolean)(implicit array: CharArr): (CharArr, CharsOff) =
  {
    var count = 0
    var continue = true
    while (offset0 + count < array.sdLength & continue)
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
  def ifHead(f: Char => Boolean)(implicit chars: CharArr) : Boolean = (chars.sdLength > offset0) &
    f(chars(offset0))
}

object CharsOff
{
  def unapply(inp: CharsOff): Option[Int] = inp match
  { case _ => Some(inp.offset0)
  }
}

/** Extractor for empty immutable heapless iterator for Chars. */
case object CharsOff0 { def unapply(inp: CharsOff)(implicit chars: CharArr): Boolean = chars.sdLength - inp.offset0 <= 0 }

/** Extractor object for immutable heapless iterator for Chars with length == 1. */
object CharsOff1
{ /** Extractor for immutable heapless iterator for Chars with length == 1. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[Char] = ife(chars.sdLength - inp.offset0 == 1, Some(chars(inp.offset0)), None)
}

/** Extractor object for immutable heapless iterator for Chars with length == 2. */
object CharsOff2
{ /** Extractor for immutable heapless iterator for Chars with length == 2. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char)] =
    ife(chars.sdLength - inp.offset0 == 2, Some((chars(inp.offset0), chars(inp.offset1))), None)
}

/** Extractor object for immutable heapless iterator for Chars with length == 3. */
object CharsOff3
{ /** Extractor for immutable heapless iterator for Chars with length == 3. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char)] =
    ife(chars.sdLength - inp.offset0 == 3, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2))), None)
}

/** Extractor object for immutable heapless iterator for Chars with length == 4. */
object CharsOff4
{ /** Extractor for immutable heapless iterator for Chars with length == 4. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char, Char)] =
    ife(chars.sdLength - inp.offset0 == 4, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2), chars(inp.offset3))), None)
}

/** Extractor object for the first element for immutable heapless iterator for Chars with at length >= 1. Use this when you don't care about the
 *  tail. */
object CharsOffHead
{ /** Extractor for the first element, for immutable heapless iterator for Chars with length >= 1. Use this when you don't care about the tail. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[Char] =
  ife(chars.sdLength - inp.offset0 >= 1, Some(chars(inp.offset0)), None)
}

/** Extractor object for the first 2 elements for immutable heapless iterator for Chars with length >= 2. Use this when you don't care about the
 *  tail. */
object CharsOffHead2
{ /** Extractor for the first 2 elements only for immutable heapless iterator for Chars with at least 2 element. Use this when you don't care about
    * the tail. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char)] =
    ife(chars.sdLength - inp.offset0 >= 2, Some((chars(inp.offset0), chars(inp.offset1))), None)
}

/** Extractor object for the first 3 elements for immutable heapless iterator for Chars with length >= 3. Use this when you don't care about the
 *  tail. */
object CharsOffHead3
{ /** Extractor for the first 3 elements only for immutable heapless iterator for Chars with at least 3 element. Use this when you don't care about
 * the tail. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char)] =
    ife(chars.sdLength - inp.offset0 >= 3, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2))), None)
}

/** Extractor object for the first 3 elements for immutable heapless iterator for Chars with length >= 3. Use this when you don't care about the
 *  tail. */
object CharsOffHead4
{ /** Extractor for the first 3 elements for immutable heapless iterator for Chars with length >= 3. Use this when you don't care about the tail */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, Char, Char, Char)] =
    ife(chars.sdLength - inp.offset0 >= 4, Some((chars(inp.offset0), chars(inp.offset1), chars(inp.offset2), chars(inp.offset3))), None)
}

/** Extractor for immutable heapless iterator for Chars with at l element. */
object CharsOff1Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 1 element. */
  def unapply(inp: CharsOff)(implicit chars: CharArr): Option[(Char, CharsOff)] =
  ife(chars.sdLength - inp.offset0 >= 1, Some((chars(inp.offset0), inp.drop1)), None)
}

/** Extractor for immutable heapless iterator for Chars with at least 2 elements. */
object CharsOff2Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 2 elements. */
  def unapply(inp: CharsOff)(implicit array: CharArr): Option[(Char, Char, CharsOff)] =
    ife(array.sdLength - inp.offset0 >= 2, Some((array(inp.offset0), (array(inp.offset1)), inp.drop2)), None)
}

/** Extractor for immutable heapless iterator for Chars with at least 3 elements. */
object CharsOff3Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 3 elements. */
  def unapply(inp: CharsOff)(implicit array: CharArr): Option[(Char, Char, Char, CharsOff)] =
    ife(array.sdLength - inp.offset0 >= 3, Some((array(inp.offset0), array(inp.offset1), array(inp.offset2), inp.drop3)), None)
}

/** Extractor for immutable heapless iterator for Chars with at least 4 elements. */
object CharsOff4Tail
{ /** Extractor for immutable heapless iterator for Chars with at least 4 elements. */
  def unapply(inp: CharsOff)(implicit array: CharArr): Option[(Char, Char, Char, Char, CharsOff)] =
  ife(array.sdLength - inp.offset0 >= 4,
    Some((array(inp.offset0), array(inp.offset1), array(inp.offset2), array(inp.offset3), inp.drop4)),
    None)
}