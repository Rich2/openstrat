package ostrat
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance, reflect.ClassTag

final class Refs[+A <: AnyRef](val array: Array[A] @uncheckedVariance) extends AnyVal with ArrImut[A]
{ type ThisT = Refs[A] @uncheckedVariance
  override def buildThis(length: Int): Refs[A] = new Refs(new Array[AnyRef](length).asInstanceOf[Array[A]])
  override def length: Int = array.length
  override def apply(index: Int): A = array(index)
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = array(i) = value
  @inline def drop1(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] = drop(1)

  import collection.immutable._
  def toArraySeq[AA >: A <: AnyRef]: ArraySeq[A] = new ArraySeq.ofRef(array)

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

  implicit def bindImplicit[B <: AnyRef](implicit ct: ClassTag[B]): Bind[Refs[B]] = new Bind[Refs[B]]
  {
    override def bind[A](orig: ArrayLike[A], f: A => Refs[B]): Refs[B] =
    { val buff = new ArrayBuffer[B]
      orig.foreach(a => buff.addAll(f(a).array))
      new Refs[B](buff.toArray)
    }
  }
}

object Refs1
{ def unapply[A <: AnyRef](refs: Refs[A])(implicit ct: ClassTag[A]): Option[(A, Refs[A])] = refs match
  { case arr if refs.nonEmpty => Some((refs.head, refs.drop1))
    case _ => None
  }
}

class RefsBuff[A <: AnyRef](val buffer: ArrayBuffer[A]) extends AnyVal with ArrBuff[A]
{ override def length: Int = buffer.length
  override def apply(index: Int): A = buffer(index)
}