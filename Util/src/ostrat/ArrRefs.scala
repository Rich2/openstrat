package ostrat
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance, reflect.ClassTag

final class Refs[+A <: AnyRef](val array: Array[A] @uncheckedVariance) extends AnyVal with ArrImut[A]
{ type ThisT = Refs[A] @uncheckedVariance
  override def buildThis(length: Int): Refs[A] = new Refs(new Array[AnyRef](length).asInstanceOf[Array[A]])
  override def length: Int = array.length
  override def apply(index: Int): A = array(index)
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = array(i) = value
  @inline def drop1(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] = drop(1)
  def offset(value: Int): RefsOff[A] @uncheckedVariance = new RefsOff[A](value)
  def offset0: RefsOff[A @uncheckedVariance] = offset(0)

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

  implicit def bindImplicit[B <: AnyRef](implicit ct: ClassTag[B]): ArrFlatBuild[Refs[B]] = new ArrFlatBuild[Refs[B]]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Refs[B]): Refs[B] =
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


object Refs1Tail
{
  def unapply[A <: AnyRef](refs: Refs[A])(implicit ct: ClassTag[A]): Option[(A, Refs[A])] = refs match
  { case arr if refs.nonEmpty => Some((refs.head, refs.drop1))
    case _ => None
  }
}

object Refs2Tail
{
  def unapply[A <: AnyRef](refs: Refs[A])(implicit ct: ClassTag[A]): Option[(A, A, Refs[A])] = refs match
  { case arr if refs.nonEmpty => Some((refs(0), refs(1), refs.drop1))
    case _ => None
  }
}

object RefsHead
{ /** Extractor for the head of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[A] = refs match
  { case refs if refs.length >= 1 => Some(refs(0))
    case _ => None
  }
}

object RefsHead2
{ /** Extractor for the head 2 elements of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A)] = refs match
  { case refs if refs.length >= 2 => Some((refs(0), refs(1)))
    case _ => None
  }
}

object RefsHead3
{ /** Extractor for the head of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A)] = refs match
  { case refs if refs.length >= 3 => Some((refs(0), refs(1), refs(2)))
    case _ => None
  }
}

object RefsHead4
{ /** Extractor for the head of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A)] = refs match
  { case refs if refs.length >= 4 => Some((refs(0), refs(1), refs(2), refs(3)))
    case _ => None
  }
}
/** Extractor function object for a Good Refs Sequence of length 0. */
case object GoodRefs0
{ /** Extractor method for a Good Refs Sequence of length 0. */
  def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Boolean = refs match
  { case Good(refs) if refs.length == 0 => true
    case _ => false
  }
}

/** Extractor function object for a Good Refs Sequence of length 1. */
object GoodRefs1
{ /** Extractor method for a Good Refs Sequence of length 1. */
  def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[A] = refs match
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

object GoodRefs3
{ def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[(A, A, A)] = refs match
{ case Good(refs) if refs.length == 3 => Some((refs(0), refs(1), refs(2)))
  case _ => None
}
}

object GoodRefs4
{ def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[(A, A, A, A)] = refs match
  { case Good(refs) if refs.length == 4 => Some((refs(0), refs(1),refs(2), refs(3)))
    case _ => None
  }
}

/** Immutable heapless iterator for Refs. */
class RefsOff[A <: AnyRef](val offset0: Int) extends AnyVal with ArrOff[A, Refs[A]]
{ override def apply(index: Int)(implicit refs: Refs[A]) = refs(index)
  def drop(n: Int): RefsOff[A] = new RefsOff[A](offset0 + n)
  def drop1: RefsOff[A] = new RefsOff(offset0 + 1)
  def drop2: RefsOff[A] = new RefsOff(offset0 + 2)
  def length(implicit refs: Refs[A]): Int = refs.length - offset0
  def span(p: A => Boolean)(implicit refs: Refs[A], ct: ClassTag[A]): (Refs[A], RefsOff[A]) =
  {
    var count = 0
    var continue = true
    while (offset0 + count < refs.length & continue)
    {
      if (p(refs(offset0 + count))) count += 1
      else continue = false
    }
    val newArray: Array[A] = new Array[A](count)
    iUntilForeach(0, count){i =>
      newArray(i) = refs(offset0 + i)}
    (new Refs(newArray), drop(count))
  }
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: A => Boolean)(implicit refs: Refs[A]) : Boolean = (refs.length > offset0) & f(refs(offset0))

}

/** Extractor for empty immutable heapless iterator for Refs. */
case object RefsOff0
{ /** Extractor for empty immutable heapless iterator for Refs. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Boolean = inp.length <= 0
}

/** Extractor object for the head only for immutable heapless iterator for Refs with at least 1 element. */
object RefsOff1
{ /** Extractor for the head only for immutable heapless iterator for Refs with at least 1 element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[A] =
    ife(inp.length >= 1, Some(inp(0)), None)
}

/** Extractor for immutable heapless iterator for Refs with at least l element. */
object RefsOff1Tail
{ /** Extractor for immutable heapless iterator for Refs with at least l element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[(A, RefsOff[A])] =
    ife(inp.length >= 1, Some(((inp(0)), inp.drop1)), None)
}

object RefsOff2Tail
{
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[(A, A, RefsOff[A])] =
    ife(inp.length >= 2, Some((inp(0), inp(1), inp.drop2)), None)
}

object RefsOffHead
{
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[A] =
    ife(inp.length  >= 1, Some(inp(0)), None)
}

/*
class RefsBuff[A <: AnyRef](val buffer: ArrayBuffer[A]) extends AnyVal with BufferLike[A]
{ override def length: Int = buffer.length
  override def apply(index: Int): A = buffer(index)
}*/
