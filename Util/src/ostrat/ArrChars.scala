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
  implicit val bindImplicit: Bind[Chars] = new Bind[Chars]
  {
    override def bind[A](orig: ArrayLike[A], f: A => Chars): Chars =
    { val buff = new ArrayBuffer[Char]
      orig.foreach(a => buff.addAll(f(a).array))
      new Chars(buff.toArray)
    }
  }
}

class CharsBuff(val buffer: ArrayBuffer[Char]) extends AnyVal with ArrBuff[Char]
{ override def length: Int = buffer.length
  override def apply(index: Int): Char = buffer(index)
}

/** Immutable heapless iterator for Char arrays. */
class CharsOff(val offset: Int) extends AnyVal
{ def drop(n: Int): CharsOff = new CharsOff(offset + n)
  def drop1: CharsOff = new CharsOff(offset + 1)
  def drop2: CharsOff = new CharsOff(offset + 2)
  def length(implicit array: Array[Char]): Int = array.length - offset
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
  def ifHead(f: Char => Boolean)(implicit array: Array[Char]) : Boolean = (array.length > offset) & f(array(offset))
}

/** Extractor for empty immutable heapless iterator for Char arrays. */
case object CharsOff0 { def unapply(inp: CharsOff)(implicit array: Chars): Boolean = array.length - inp.offset <= 0 }

object CharsOffHead
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[Char] =
  ife(array.length - inp.offset >= 1, Some(array(inp.offset)), None)
}


object CharsOff1
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[(Char, CharsOff)] =
  ife(array.length - inp.offset >= 1, Some((array(inp.offset), inp.drop1)), None)
}

object CharsOff2
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[(Char, Char, CharsOff)] =
    ife(array.length - inp.offset >= 2, Some((array(inp.offset), (array(inp.offset + 1)), inp.drop2)), None)
}

object CharsOff3
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[(Char, Char, Char, CharsOff)] =
    ife(array.length - inp.offset >= 3, Some((array(inp.offset), array(inp.offset + 1), array(inp.offset + 2), inp.drop(3))), None)
}

object CharsOff4
{ def unapply(inp: CharsOff)(implicit array: Chars): Option[(Char, Char, Char, Char, CharsOff)] =
  ife(array.length - inp.offset >= 4,
    Some((array(inp.offset), array(inp.offset + 1), array(inp.offset + 2), array(inp.offset + 3), inp.drop(4))),
    None)
}