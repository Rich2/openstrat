/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pParse.*, annotation.unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** The Multiple type class allow you to represent multiple values of type A. Implicit conversion in package object. To create a Multiple instance follow the
 * value by the "*" symbol followed by an integer. There is a n implicit conversion from an object of type to a Multiple of type T with quantity of 1. */
case class Multiple[+A](value: A, num: Int)
{ /** multiply the [[Multiple]] number with the operand. */
  def * (operand: Int): Multiple[A] = Multiple(value, num * operand)

  override def toString = "Multiple" + (value.toString + "; " + num.toString).enParenth

  def foreach(f: A => Unit): Unit = num.doTimes(() => f(value))

  def map[B](f: A => B): Multiple[B] = Multiple[B](f(value), num)

  def flatMap[B](f: A => Multiple[B]): Multiple[B] =
  { val res: Multiple[B] = f(value)
    Multiple[B](res.value, res.num * num)
  }

  def toArr[ArrA <: Arr[A]@uncheckedVariance](implicit build: BuilderArrMap[A, ArrA]@uncheckedVariance): ArrA =
  { val res: ArrA = build.uninitialised(num)
    iUntilForeach(num){i => res.setElemUnsafe(i, value)}
    res
  }
}

/** Companion object for the [[Multiple]][+A] type class. */
object Multiple
{
  given arrMapBuilderEv[A](using ctA: ClassTag[A]): MultipleArrMapBuilder[A] = new MultipleArrMapBuilder[A]

  given eqTEv[A](using evA: EqT[A]): EqT[Multiple[A]] = (a1, a2) => (a1.num == a2.num) && evA.eqT(a1.value, a2.value)

  implicit class RefsImplicit[A](thisRefs: RArr[Multiple[A]])
  { /** The total number of elements in this sequence of [[Multiple]]s. */
    def numSingles: Int = thisRefs.sumBy(_.num)

    /** Converts this sequence of [[Multiple]]s to an [[Arr]] of the type of the [[Multiple]]. */
    def toArr[R <: Arr[A]](implicit builder: BuilderArrMap[A, R]): R =
    { val newLen = thisRefs.numSingles
      val res = builder.uninitialised(newLen)
      var i = 0
      thisRefs.foreach { multi =>
        iUntilForeach(multi.num) { j => res.setElemUnsafe(i + j, multi.value) }
        i = i + multi.num
      }
      res
    }

    def toColl[R](builder: BuilderMap[A, R]): R =
    { val buff = builder.newBuff()
      thisRefs.foreach { multi => iUntilForeach(multi.num) { _ => builder.buffGrow(buff, multi.value) } }
      builder.buffToSeqLike(buff)
    }
  }

  /** [[Show]] type class instance / evidence for full show of [[Multiple]] class. */
  def showFullEv[A](using evA: Show[A]): Show2[A, Int, Multiple[A]] = Show2[A, Int, Multiple[A]]("Multiple", "value", _.value, "num", _.num)

  /** [[Unshow]] type class instance / evidence for [[Multiple]] class. */
  given unshowEv[A](using evA: Unshow[A]): UnshowMultiple[A] = new UnshowMultiple[A]()

  class UnshowMultiple[A]()(implicit val evA: Unshow[A]) extends Unshow[Multiple[A]]
  { override def typeStr: String = "Multiple"
    override def useMultiple: Boolean = false

    override def fromExpr(expr: Expr): ExcMon[Multiple[A]] =  expr match
    { case InfixOpExpr(left, OperatorPrec1Token(startPosn, "*"), IntExpr(i)) => evA.fromExpr(left).map(a => Multiple(a, i))
      case AlphaMaybeSquareParenth(name,  RArr2(Statement(e1), Statement(IntExpr(i)))) if name == "Multiple" => evA.fromExpr(e1).map{ a => Multiple(a, i) }
      case expr => evA.fromExpr(expr).map(a => Multiple(a, 1))
    }

    def fromArrExpr(inp: Arr[Expr]): ExcMon[RArr[Multiple[A]]] = inp.mapErrBi(fromExpr(_))

    /** Collection from [[Arr]] of [[Expr]]. */
    def collFromArrExpr[R](inp: Arr[Expr], builderColl: BuilderMap[A, R]): ExcMon[R] = fromArrExpr(inp).map(_.toColl(builderColl))

    /** Collection from [[Arr]] of [[Statement]]. */
    def collFromArrStatement[R](inp: Arr[Statement], builderColl: BuilderMap[A, R]): ExcMon[R] = collFromArrExpr(inp.map(_.expr), builderColl)
  }

  /** Collection from [[Arr]] of [[Expr]]. */
  def collFromArrExpr[Ae, A](inp: Arr[Expr])(using evA: Unshow[Ae], builderColl: BuilderMap[Ae, A]): ExcMon[A] =
    unshowEv.fromArrExpr(inp).map(_.toColl(builderColl))

  /** Collection from [[Arr]] of [[Statement]]. */
  def collFromArrStatement[A, R](inp: Arr[Statement])(implicit evA: Unshow[A], builderColl: BuilderMap[A, R]): ExcMon[R] =
    unshowEv(using evA).collFromArrExpr(inp.map(_.expr), builderColl)  
}

object MultExt
{
  extension [A](thisA: A)    
  { /** Extension method on any type creates Multiple class of that type. */
    def *(operand: Int): Multiple[A] = Multiple(thisA, operand)
  }

  implicit def toMultipleImplicit[A](value: A): Multiple[A] = Multiple(value, 1)
}

class MultipleArr[A](intArray: Array[Int], valueArray: Array[A]) extends Arr[Multiple[A]]
{ type ThisT = MultipleArr[A]
  override def typeStr: String = "MultipleArr"

  def numSingles: Int = this.sumBy(_.num)

  def toSinglesArr[ArrA <: Arr[A]](implicit build: BuilderArrMap[A, ArrA]): ArrA =
  { val res = build.uninitialised(numSingles)
    var i = 0
    foreach{ m => iUntilForeach(m.num){ _ => res.setElemUnsafe(i, m.value); i += 1 } }
    res
  }
  
  override def elem(index: Int): Multiple[A] = new Multiple[A](valueArray(index), intArray(index))
  override def apply(index: Int): Multiple[A] = new Multiple[A](valueArray(index), intArray(index))
  override def length: Int = intArray.length
  override def numElems: Int = intArray.length
  override def setElemUnsafe(index: Int, newElem: Multiple[A]): Unit = { valueArray(index) = newElem.value; intArray(index) =newElem.num }
  override def fElemStr: Multiple[A] => String = _.toString
  def unsafeSameSize(length: Int)(implicit ct: ClassTag[A]): ThisT = new MultipleArr[A](new Array[Int](length), new Array[A](length))
}

extension[A](thisSeq: Seq[Multiple[A]])
{ /** Extension method. The number of single values of type A in this [[Seq]] of [[Multiple]]s. */
  def numSingles: Int = thisSeq.sumBy (_.num)

  /** Extension method. Converts this [[Seq]] of [[Multiple]]s, to an [[Arr]] of the Single values
   * of type A. The appropriate Arr type is found by implicit look up for type A. */
  def toSinglesArr[ArrA <: Arr[A]](implicit build: BuilderArrMap[A, ArrA]): ArrA =
  { val res = build.uninitialised(numSingles)
    var i = 0
    thisSeq.foreach { m => iUntilForeach(m.num) { _ => res.setElemUnsafe(i, m.value); i += 1 } }
    res
  }

  /** Foreachs over each single value  of this [[Seq]] of [[Multiple]]s with an index. */
  def iForeachSingle(f: (Int, A) => Unit): Unit =
  { var i = 0
    thisSeq.foreach{m => repeat(m.num){ f(i, m.value); i += 1} }
  }
}

/** Specialised buffer class for [[Multiple]] elements. */
class MultipleBuff[A](val numBuffer: ArrayBuffer[Int], val valuesBuffer: ArrayBuffer[A]) extends Buff[Multiple[A]]
{ override type ThisT = MultipleBuff[A]
  override def typeStr: String = "MultipleBuff"
  override def grow(newElem: Multiple[A]): Unit = { numBuffer.append(newElem.num); valuesBuffer.append(newElem.value) }
  override def apply(index: Int): Multiple[A] = new Multiple[A](valuesBuffer(index), numBuffer(index))
  override def elem(index: Int): Multiple[A] = new Multiple[A](valuesBuffer(index), numBuffer(index))
  override def length: Int = numBuffer.length
  override def numElems: Int = numBuffer.length
  override def setElemUnsafe(index: Int, newElem: Multiple[A]): Unit = { numBuffer(index) = newElem.num; valuesBuffer(index) = newElem.value }
  override def fElemStr: Multiple[A] => String = _.toString

  override def mutateElemUnsafe(index: Int, f: Multiple[A] => Multiple[A]): Unit =
  { val newMulti = f(apply(index))
    numBuffer(index) = newMulti.num
    valuesBuffer(index) = newMulti.value
  }
}

object MultipleBuff
{ def apply[A](initLen: Int = 4): MultipleBuff[A] = new MultipleBuff[A](new ArrayBuffer[Int](initLen), new ArrayBuffer[A](initLen))
}

class MultipleArrMapBuilder[A](implicit ct: ClassTag[A]) extends BuilderArrMap[Multiple[A], MultipleArr[A]]
{ override type BuffT = MultipleBuff[A]
  override def buffGrow(buff: MultipleBuff[A], newElem: Multiple[A]): Unit = buff.grow(newElem)
  override def uninitialised(length: Int): MultipleArr[A] = new MultipleArr[A](new Array[Int](length), new Array[A](length))
  override def indexSet(seqLike: MultipleArr[A], index: Int, newElem: Multiple[A]): Unit = { seqLike.setElemUnsafe(index, newElem) }
  override def newBuff(length: Int): MultipleBuff[A] = MultipleBuff(length)
  override def buffToSeqLike(buff: MultipleBuff[A]): MultipleArr[A] = new MultipleArr[A](buff.numBuffer.toArray, buff.valuesBuffer.toArray)
}