package ostrat
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance, reflect.ClassTag

final class Refs[+A <: AnyRef](val array: Array[A] @uncheckedVariance) extends AnyVal with ArrImut[A]
{ type ThisT = Refs[A] @uncheckedVariance
  override def buildThis(length: Int): Refs[A] = new Refs(new Array[AnyRef](length).asInstanceOf[Array[A]])
  override def length: Int = array.length
  override def apply(index: Int): A = array(index)
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = array(i) = value
  @inline def drop1(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] = drop(1)
  def refsOffsetter: RefsOff[A] @uncheckedVariance = new RefsOff[A](0)
 // import collection.immutable._
 // def toArraySeq[AA >: A <: AnyRef]: ArraySeq[A] = new ArraySeq.ofRef(array)

  override def unsafeArrayCopy(operand: Array[A] @uncheckedVariance, offset: Int, copyLength: Int): Unit =
  {  array.copyToArray(array, offset, copyLength); ()}

  def drop(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] =
  { val newArray = new Array[A]((length - 1).min0)
    iUntilForeach(1, length)(i => newArray(i - 1) = array(i))
    new Refs(newArray)
  }

  def :+ [AA >: A <: AnyRef](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
  { val newArray = new Array[AA](length + 1)
    array.copyToArray(newArray)
    newArray(length) = op
    new Refs(newArray)
  }

  def +: [AA >: A <: AnyRef](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
  { val newArray = new Array[AA](length + 1)
    newArray(0) = op
    array.copyToArray(newArray, 1)

    new Refs(newArray)
  }

  def ++ [AA >: A <: AnyRef](op: Refs[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
  { val newArray = new Array[AA](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Refs(newArray)
  }

  def ifAppend[AA >: A <: AnyRef](b: Boolean, newElems: => Refs[AA])(implicit ct: ClassTag[AA]): Refs[AA] = ife(b,this ++ newElems, this)
  def optAppend[AA >: A <: AnyRef](optElem: Option[AA]@uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] = optElem.fld(this, :+ _)
  def optAppends[AA >: A <: AnyRef](optElems: Option[Refs[AA]])(implicit ct: ClassTag[AA]): Refs[AA] = optElems.fld[Refs[AA]](this, ++ _)
}

object Refs
{ def apply[A <: AnyRef](input: A*)(implicit ct: ClassTag[A]): Refs[A] = new Refs(input.toArray)

  implicit def bindImplicit[B <: AnyRef](implicit ct: ClassTag[B]): ArrBinder[Refs[B]] = new ArrBinder[Refs[B]]
  {
    override def bind[A](orig: ArrayLike[A], f: A => Refs[B]): Refs[B] =
    { val buff = new ArrayBuffer[B]
      orig.foreach(a => buff.addAll(f(a).array))
      new Refs[B](buff.toArray)
    }
  }

  def showImplicit[A <: AnyRef](implicit evInA: Show[A]): Show[Refs[A]] = new pParse.ShowSeqLike[A, Refs[A]]
  { val evA = evInA
    def showComma(obj: ostrat.Refs[A]): String = ???
    def showSemi(obj: ostrat.Refs[A]): String = ???
  }
}

/** Extractor object for empty Refs[A <: AnyRef]. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs0
{ /** Extractor for empty Refs[A <: AnyRef]. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Boolean = refs.length == 0
}

/** Extractor object for Refs[A <: AnyRef] of length == 1. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs1
{ /** Extractor for Refs[A <: AnyRef] of length == 1. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[A] = refs.length match
  { case 1 => Some(refs(0))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 2. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs2
{ /** Extractor for Refs[A <: AnyRef] of length == 2. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A)] = refs.length match
  { case 2 => Some((refs(0), refs(1)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 3. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs3
{ /** Extractor for Refs[A <: AnyRef] of length == 3, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A)] = refs.length match
  { case 3 => Some((refs(0), refs(1), refs(2)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 4. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs4
{ /** Extractor for Refs[A <: AnyRef] of length == 4, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A)] = refs.length match
  { case 4 => Some((refs(0), refs(1), refs(2), refs(3)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 5. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs5
{ /** Extractor for Refs[A <: AnyRef] of length == 5, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A, A)] = refs.length match
  { case 5 => Some((refs(0), refs(1), refs(2), refs(3), refs(4)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 6. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs6
{ /** Extractor for Refs[A <: AnyRef] of length == 6, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A, A, A)] = refs.length match
  { case 6 => Some((refs(0), refs(1), refs(2), refs(3), refs(4), refs(5)))
    case _ => None
  }
}

/** Immutable heapless iterator for Refs. */
class RefsOff[A <: AnyRef](val offset: Int) extends AnyVal
{ def drop(n: Int): RefsOff[A] = new RefsOff[A](offset + n)
  def drop1: RefsOff[A] = new RefsOff(offset + 1)
  def drop2: RefsOff[A] = new RefsOff(offset + 2)
  def length(implicit refs: Refs[A]): Int = refs.length - offset
  def span(p: A => Boolean)(implicit refs: Refs[A], ct: ClassTag[A]): (Refs[A], RefsOff[A]) =
  {
    var count = 0
    var continue = true
    while (offset + count < refs.length & continue)
    {
      if (p(refs(offset + count))) count += 1
      else continue = false
    }
    val newArray: Array[A] = new Array[A](count)
    iUntilForeach(0, count){i =>
      newArray(i) = refs(offset + i)}
    (new Refs(newArray), drop(count))
  }
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: A => Boolean)(implicit refs: Refs[A]) : Boolean = (refs.length > offset) & f(refs(offset))
}

/** Extractor for empty immutable heapless iterator for Refs. */
case object RefsOff0
{ /** Extractor for empty immutable heapless iterator for Refs. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Boolean = refs.length - inp.offset <= 0
}

/** Extractor object for the head only for immutable heapless iterator for Refs with at least 1 element. */
object RefsOffHead
{ /** Extractor for the head only for immutable heapless iterator for Refs with at least 1 element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[A] =
    ife(refs.length - inp.offset >= 1, Some(refs(inp.offset)), None)
}

/** Extractor for immutable heapless iterator for Refs with at l element. */
object RefsOff1
{ /** Extractor for immutable heapless iterator for Refs with at l element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[(A, RefsOff[A])] =
  ife(refs.length - inp.offset >= 1, Some((refs(inp.offset), inp.drop1)), None)
}



object RefsHead
{ /** Extractor for the head of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[A] = refs match
  { case refs if refs.length >= 1 => Some(refs(0))
    case _ => None
  }
}

object Refs1Tail
{
  def unapply[A <: AnyRef](refs: Refs[A])(implicit ct: ClassTag[A]): Option[(A, Refs[A])] = refs match
  { case arr if refs.nonEmpty => Some((refs.head, refs.drop1))
    case _ => None
  }
}

object GoodRefs1
{ def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[A] = refs match
  { case Good(refs) if refs.length == 1 => Some(refs.head)
    case _ => None
  }
}

object GoodRefs2
{ def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[(A, A)] = refs match
  { case Good(refs) if refs.length == 2 => Some((refs(0), refs(1)))
    case _ => None
  }
}

/*
class RefsBuff[A <: AnyRef](val buffer: ArrayBuffer[A]) extends AnyVal with BufferLike[A]
{ override def length: Int = buffer.length
  override def apply(index: Int): A = buffer(index)
}*/
