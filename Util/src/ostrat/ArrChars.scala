package ostrat
import collection.mutable.ArrayBuffer

class Chars(val array: Array[Char]) extends AnyVal with ArrImut[Char]
{ type ThisT = Chars
  override def buildThis(length: Int): Chars = new Chars(new Array[Char](length))
  override def length: Int = array.length
  override def apply(index: Int): Char = array(index)
  override def unsafeSetElem(i: Int, value: Char): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Char], offset: Int, copyLength: Int): Unit = array.copyToArray(array, offset, copyLength)


  def ++ (op: Chars): Chars =
  { val newArray = new Array[Char](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Chars(newArray)
  }

  def charsOffsetter: CharsOff = new CharsOff(0)
  def mkString: String = array.mkString

}

object Chars
{ def apply(input: Char*): Chars = new Chars(input.toArray)
  implicit val bindImplicit: ArrFlatBuild[Chars] = new ArrFlatBuild[Chars]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Chars): Chars =
    { val buff = new ArrayBuffer[Char]
      orig.foreach(a => buff.addAll(f(a).array))
      new Chars(buff.toArray)
    }
  }
}

/*class CharsBuff(val buffer: ArrayBuffer[Char]) extends AnyVal with BufferLike[Char]
{ override def length: Int = buffer.length
  override def apply(index: Int): Char = buffer(index)
}*/

/** Immutable heapless iterator for Char arrays. */
class CharsOff(val offset: Int) extends AnyVal
{ def drop(n: Int): CharsOff = new CharsOff(offset + n)
  def drop1: CharsOff = new CharsOff(offset + 1)
  def drop2: CharsOff = new CharsOff(offset + 2)
  def length(implicit chars: Chars): Int = chars.length - offset
  def span(p: Char => Boolean)(implicit array: Chars): (Chars, CharsOff) =
  {
    var count = 0
    var continue = true
    while (offset + count < array.length & continue)
    {
      if (p(array(offset + count))) count += 1
      else continue = false
    }
    val newArray: Array[Char] = new Array[Char](count)
    iUntilForeach(0, count){i =>
      newArray(i) = array(offset + i)}
    (new Chars(newArray), drop(count))
  }
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: Char => Boolean)(implicit chars: Chars) : Boolean = (chars.length > offset) &
    f(chars(offset))
}

/** Extractor for empty immutable heapless iterator for Chars. */
case object CharsOff0 { def unapply(inp: CharsOff)(implicit chars: Chars): Boolean = chars.length - inp.offset <= 0 }

/** Extractor object for immutable heapless iterator for Chars with length == 1. */
object CharsOff1
{ /** Extractor for immutable heapless iterator for Chars with length == 1. */
  def unapply(inp: CharsOff)(implicit chars: Chars): Option[Char] = ife(chars.length - inp.offset == 1, Some(chars(inp.offset)), None)
}


/** Extractor object for the head only for immutable heapless iterator for Chars with at least 1 element. */
object CharsOffHead
{ /** Extractor for the 1st element only, for immutable heapless iterator for Chars with at least 1 element. */
  def unapply(inp: CharsOff)(implicit chars: Chars): Option[Char] =
  ife(chars.length - inp.offset >= 1, Some(chars(inp.offset)), None)
}

/** Extractor object for the head 2 elements only for immutable heapless iterator for Chars with at least 2 element. */
object CharsOffHead2
{ /** Extractor for the head 2 elements only for immutable heapless iterator for Chars with at least 2 element. Use this when you don't care about the tail */
  def unapply(inp: CharsOff)(implicit chars: Chars): Option[(Char, Char)] =
    ife(chars.length - inp.offset >= 2, Some((chars(inp.offset), chars(inp.offset + 1))), None)
}



/** Extractor for immutable heapless iterator for Chars with at l element. */
object CharsOff1Tail
{ /** Extractor for immutable heapless iterator for Chars with at l element. */
  def unapply(inp: CharsOff)(implicit chars: Chars): Option[(Char, CharsOff)] =
  ife(chars.length - inp.offset >= 1, Some((chars(inp.offset), inp.drop1)), None)
}

object CharsOff2Tail
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[(Char, Char, CharsOff)] =
    ife(array.length - inp.offset >= 2, Some((array(inp.offset), (array(inp.offset + 1)), inp.drop2)), None)
}

object CharsOff3Tail
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[(Char, Char, Char, CharsOff)] =
    ife(array.length - inp.offset >= 3, Some((array(inp.offset), array(inp.offset + 1), array(inp.offset + 2), inp.drop(3))), None)
}

object CharsOff4Tail
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[(Char, Char, Char, Char, CharsOff)] =
  ife(array.length - inp.offset >= 4,
    Some((array(inp.offset), array(inp.offset + 1), array(inp.offset + 2), array(inp.offset + 3), inp.drop(4))),
    None)
}