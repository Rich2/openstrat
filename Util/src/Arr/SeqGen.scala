/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, collection.immutable._

/** This the base trait for all efficient sequence collections based on Array like classes, Arrays, ArrayBuffers etc. The final classes compile time
 *  wrap the platform Array and buffer classes. So currently there are just two classes for each type A, An ArrImut that wraps a standard immutable
 *  Array to produce an immutable array, and a ArrBuff that wraps an ArrayBuffer. Currently this just in a standard ArrayBuffer. Where A is a compound
 *  value types or an AnyVal type. */
trait SeqGen[+A] extends Any with DataGen[A @uncheckedVariance]
{ /** The final type of this object. */
  type ThisT <: SeqGen[A]

  /** Method for keeping the typer happy when returning this as an instance of ThisT. */
  @inline def returnThis: ThisT = this.asInstanceOf[ThisT]

  /** apply method accesses the individual elements of the sequence by 0 based index. */
  @inline def apply(index: Int): A = indexData(index)//: A = apply(index)

  /** The first element of this sequence. */
  @inline def head: A = apply(0)

  /** The last element of this sequence. */
  @inline def last: A = apply(elemsNum - 1)

  /** Is this sequence empty? */
  @inline def empty: Boolean = elemsNum <= 0

  /** Is this sequence non empty? */
  @inline def nonEmpty: Boolean = elemsNum > 0

  /** Folds over the non existence / existence of a head element. The first parameter is a value for an empty sequence, the second parameter passed as a separate parameter list is a function on the head element. */
  def headFold[B](noHead: => B)(ifHead: A => B): B = ife(elemsNum >= 1, ifHead(head), noHead)

  /** Folds over the non existence / existence of a head element. If the sequence is nonEmpty applies toString to head element else returns the noHead parameter string. */
  def headFoldToString[B](noHead: => String): String = ife(elemsNum >= 1, apply(0).toString, noHead)

  /** Folds over the non existence / existence of a last element. The first parameter is a value for an empty sequence, the second parameter passed as a separate parameter list is a function on the last element. */
  def lastFold[B](noLast: => B)(ifLast: A => B): B = ife(elemsNum >= 1, ifLast(last), noLast)

  /** if this [[SeqGen]] is nonEmpty performs the side effecting function on the head. If empty procedure is applied. */
  def ifHead[U](f: A => U): Unit = if(elemsNum >= 1) f(apply(0))

  /** Applies an index to this ArrayLike collection which cycles back to element 0, when it reaches the end of the collection. Accepts even negative
   * integers as an index value without throwing an exception. */
  @inline def cycleGet(index: Int): A = apply(index %% elemsNum)

  /** Performs a side effecting function on each element of this sequence in order. The function may return Unit. If it does return a non Unit value
   *  it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method users. */
  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < elemsNum)
    { f(apply(count))
      count = count + 1
    }
  }

  /** Index with foreach. Performs a side effecting function on the index and each element of this sequence. It takes a function as a parameter. The
   *  function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about
   *  discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0 if just
   *  the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value
   *  of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example
   *  in fold methods' (accumulator, element) => B signature. */
  def iForeach[U](f: (Int, A) => U): Unit =
  { var count = 0
    var i: Int = 0
    while(count < elemsNum )
    { f(i, apply(count))
      count+= 1
      i += 1
    }
  }

  /** Index with foreach. Performs a side effecting function on the index and each element of this sequence. It takes a function as a parameter. The
   *  function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about
   *  discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0 if just
   *  the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value
   *  of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example
   *  in fold methods' (accumulator, element) => B signature. */
  def iForeach[U](startIndex: Int)(f: (Int, A) => U): Unit =
  { var count = 0
    var i: Int = startIndex
    while(count < elemsNum )
    { f(i, apply(count))
      count+= 1
      i += 1
    }
  }

  /** Specialised map to an immutable ArrBase of B. */
  def map[B, ArrB <: SeqImut[B]](f: A => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    iForeach((i, a) => ev.arrSet(res, i, f(a)))
    res
  }

  /** Index with element map. Applies the parameter function to the index and each respective element of this sequence. The function returns an
   * element of type B and the method as a whole returns the specialised [[SeqImut]] of type B. The method has 2 versions / name overloads. The
   * default start for the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first
   * parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of putting the collection
   * element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. This method should be overridden in sub
   * classes. */
  def iMap[B, ArrB <: SeqImut[B]](f: (Int, A) => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    iForeach((i, a) => ev.arrSet(res, i, f(i, a)))
    res
  }

  /** Index with element map. Applies the parameter function to the index and each respective element of this sequence. The function returns an
   * element of type B and the method as a whole returns the specialised [[SeqImut]] of type B. The method has 2 versions / name overloads. The
   * default start for the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first
   * parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of putting the collection
   * element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. Ideally this method should be overridden in sub
   * classes. */
  def iMap[B, ArrB <: SeqImut[B]](startindex: Int)(f: (Int, A) => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    iForeach(startindex)((i, a) => ev.arrSet(res, i, f(i, a)))
    res
  }

  /** Specialised flatMap to a [[SeqImut]]. */
  def flatMap[ArrB <: SeqImut[_]](f: A => ArrB)(implicit ev: ArrFlatBuilder[ArrB]): ArrB =
  {
    val buff: ev.BuffT = ev.newBuff()
    foreach{ a =>
      val newVals = f(a)
      ev.buffGrowArr(buff, newVals)
    }
    ev.buffToBB(buff)
  }

  /** Index with element flatMap. Applies the parameter function to the index and each respective element of this sequence. The function returns a
   * [[SeqImut]] of elements of type B and the method as a whole flattens and then returns the specialised [[SeqImut]] of type B. The method has 2
   * versions / name overloads. The default start for the index is 0 if just the function parameter is passed. The second version name overload takes
   * an [[Int]] for the first parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of
   * putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. Ideally this method should
   * be overridden in sub classes. */
  def iFlatMap[ArrB <: SeqImut[_]](f: (Int, A) => ArrB)(implicit build: ArrFlatBuilder[ArrB]): ArrB =
  { val buff: build.BuffT = build.newBuff()
    var i: Int = 0
    while (i < elemsNum)
    { val newArr = f(i, apply(i));
      build.buffGrowArr(buff, newArr)
      i += 1
    }
    build.buffToBB(buff)
  }

  /** Index with element flatMap. Applies the parameter function to the index and each respective element of this sequence. The function returns a
   * [[SeqImut]] of elements of type B and the method as a whole flattens and then returns the specialised [[SeqImut]] of type B. The method has 2
   * versions / name overloads. The default start for the index is 0 if just the function parameter is passed. The second version name overload takes
   * an [[Int]] for the first parameter list, to set the start value of the index. Note the function signature follows the foreach based convention of
   * putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. Ideally this method should
   * be overridden in sub classes. */
  def iFlatMap[ArrB <: SeqImut[_]](iInit: Int)(f: (Int, A) => ArrB)(implicit build: ArrFlatBuilder[ArrB]): ArrB =
  { val buff: build.BuffT = build.newBuff()
    var count: Int = 0
    while (count < elemsNum)
    { val newElems = f(count + iInit, apply(count))
      build.buffGrowArr(buff, newElems)
      count += 1
    }
    build.buffToBB(buff)
  }

  /** Takes a second collection as a parameter and zips the elements of this collection and the operand collection and applies the specialised map
   * function from type A and type B to type C. */
  def zipMap[B, C, ArrC <: SeqImut[C]](operator: SeqGen[B])(f: (A, B) => C)(implicit ev: ArrBuilder[C, ArrC]): ArrC =
  { val newLen = elemsNum.min(operator.elemsNum)
    val res = ev.newArr(newLen)
    var count = 0
    while(count < newLen)
    { val newElem = f(apply(count), operator.apply(count))
      ev.arrSet(res, count, newElem)
      count += 1
    }
    res
  }

  /** Takes a second collection and third collections as parameters and zips the elements of this collection and the operand collections and applies
   *  the specialised map function from type A and type B and type C to type D. */
  def zipMap2[B, C, D, ArrD <: SeqImut[D]](operator1: SeqGen[B], operator2: SeqGen[C])(f: (A, B, C) => D)(implicit ev: ArrBuilder[D, ArrD]): ArrD =
  { val newLen = elemsNum.min(operator1.elemsNum).min(operator2.elemsNum)
    val res = ev.newArr(newLen)
    var count = 0
    while(count < newLen)
    { val newElem = f(apply(count), operator1.apply(count), operator2.apply(count))
      ev.arrSet(res, count, newElem)
      count += 1
    }
    res
  }

  /* Maps from A to B like normal map,but has an additional accumulator of type C that is discarded once the traversal is completed. Note the function
   * signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   *  (accumulator, element) => B signature. */
  def mapWithAcc[B, ArrB <: SeqImut[B], C](initC: C)(f: (C, A) => (B, C))(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    var accC: C = initC
    iForeach({ (i, a) =>
          val (newB, newC) = f(accC, a)
          res.unsafeSetElem(i, newB)
          accC = newC
        })
    res
  }

  def eMap[B, ArrB <: SeqImut[B]](f: A => EMon[B])(implicit ev: ArrBuilder[B, ArrB]): EMon[ArrB] =
  { val acc = ev.newBuff()
    var continue = true
    var count = 0
    var errs: Strings = Strings()
    while(count < elemsNum & continue == true)
      f(apply(count)).foldErrs { g => ev.buffGrow(acc, g); count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(ev.buffToBB(acc)), Bad(errs))
  }

  def eMapList[B](f: A => EMon[B]): EMon[List[B]] =
  { var acc: List[B] = Nil
    var continue = true
    var count = 0
    var errs: Strings = Strings()
    while(count < elemsNum & continue == true)
      f(apply(count)).foldErrs { g => acc ::= g; count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(acc.reverse), Bad(errs))
  }

  /** map 2 elements of A to 1 element of B. Ignores the last element on a collection of odd numbered length. */
  def map2To1[B, ArrB <: SeqImut[B]](f: (A, A) => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    var count = 0
    while (count + 1  < elemsNum)
    {  ev.arrSet(res, count, f(apply(count), apply(count + 1)))
      count += 2
    }
    res
  }

  def filter[ArrA <: SeqImut[A] @uncheckedVariance](f: A => Boolean)(implicit ev: ArrBuilder[A, ArrA] @uncheckedVariance): ArrA =
  { val buff = ev.newBuff()
    foreach(a => oif(f(a), ev.buffGrow(buff, a)))
    ev.buffToBB(buff)
  }

  def filterNot[ArrA <: SeqImut[A] @uncheckedVariance](f: A => Boolean)(implicit ev: ArrBuilder[A, ArrA] @uncheckedVariance): ArrA =
  { val buff = ev.newBuff()
    foreach(a => oif(!f(a), ev.buffGrow(buff, a)))
    ev.buffToBB(buff)
  }

  def filterToList(f: A => Boolean): List[A] =
  { var acc: List[A] = Nil
    foreach{ a => if (f(a)) acc ::= a }
    acc.reverse
  }

  /** FlatMaps over a function from A to any Iterable. */
  def iterFlatMap[B, ArrB <: SeqImut[B]](f: A => Iterable[B])(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val buff = ev.newBuff(elemsNum)
    foreach(a => ev.buffGrowIter(buff, f(a)))
    ev.buffToBB(buff)
  }

  def foldLeft[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    foreach(a => acc = f(acc, a))
    acc
  }

  def indexOf(elem: A @uncheckedVariance): Int =
  { var result = -1
    var count  = 0
    while (count < elemsNum & result == -1)
    { if (elem == apply(count)) result = count
    else count += 1
    }
    result
  }

  /** Return the index of the first element where predicate is true, or -1 if predicate not true forall. */
  def indexWhere(f: A => Boolean): Int =
  { var count = 0
    var result = -1
    while(count < elemsNum & result == -1)
    { if(f(apply(count))) result = count
      count += 1
    }
    result
  }

  /** Foreachs over the tail of this sequence. */
  def tailForeach[U](f: A => U): Unit =
  { var count = 1
    while(count < elemsNum) { f(apply(count)); count += 1 }
  }

  def foreachInit[U](f: A => U): Unit =
  { var count = 0
    while(count < elemsNum - 1)
    { f(apply(count))
      count += 1
    }
  }

  /** foldLeft over the tail of this sequence. */
  def tailfold[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    tailForeach(a => acc = f(acc, a))
    acc
  }

  def foldHeadTail[B](initial: B)(fHead: (B, A) => B)(fTail: (B, A) => B) =
  { var acc: B = initial
    var start: Boolean = true
    foreach { a =>
      if(start == true) { acc = fHead(acc, a); start = false }
      else acc = fTail(acc, a)
    }
    acc
  }

  /** Consider changing this name, as might not be appropriate to all sub classes. */
  def foreachReverse[U](f: A => U): Unit =
  { var count = elemsNum
    while(count > 0) { count -= 1; f(apply(count)) }
  }

  /** Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold
   *  methods' (accumulator, element) => B signature. */
  def iForeachReverse[U](f: (Int, A) => U): Unit =
  { var count = elemsNum
    while(count > 0) { count -= 1; f(count, apply(count)) }
  }

  def forAll(p: (A) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < elemsNum) if (p(apply(count))) count += 1 else acc = false
    acc
  }

  /**  Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold
   *  methods' (accumulator, element) => B signature. */
  def iForAll(p: (Int, A) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < elemsNum) if (p(count, apply(count))) count += 1 else acc = false
    acc
  }

  def contains[A1 >: A](elem: A1): Boolean =
  { var count = 0
    var res = false
    while (res == false & count < elemsNum){ if (elem == apply(count)) res = true; count += 1 }
    res
  }

  /** maps ValueProduct collection to List */
  def mapList[B <: AnyRef](f: A => B): List[B] =
  { var res: List[B] = Nil
    foreachReverse(res ::= f(_))
    res
  }

  def toStrsFold(seperator: String,  f: A => String): String =
  { var acc: String = ""
    var start = true
    foreach{ a => if(start == true) { acc = f(a); start = false } else acc = acc + seperator + f(a)}
    acc
  }

  /** Counts the number of elements that fulfil the condition A => Boolean */
  def existsCount(f: A => Boolean): Int =
  { var count = 0
    foreach(el => if (f(el)) count += 1)
    count
  }

  /** Not sure about this method. */
  def mkString(seperator: String): String = ife(elemsNum == 0, "",
    { var acc = head.toString
      var count = 1
      while(count < elemsNum)
      { acc += seperator + apply(count).toString
        count += 1
      }
      acc
    }
  )

  def toList: List[A] =
  { var acc: List[A] = Nil
    foreachReverse(acc ::= _)
    acc
  }

  def sumBy(f: A => Int): Int =
  { var acc = 0
    foreach(acc += f(_))
    acc
  }

  /** Collects values of B by applying partial function to only those elements of A, for which the PartialFunction is defined. */
  def collect[B, BB <: SeqImut[B]](pf: PartialFunction[A, B])(implicit ev: ArrBuilder[B, BB]): BB =
  { val acc = ev.newBuff()
    foreach{a => if (pf.isDefinedAt(a)) ev.buffGrow(acc, pf(a)) }
    ev.buffToBB(acc)
  }

  /** Collects a List values of B by applying partial function to only those elements of A, for which the PartialFunction is defined. */
  def collectList[B](pf: PartialFunction[A, B]): List[B] =
  { var acc: List[B] = Nil
    foreach{a => if (pf.isDefinedAt(a)) acc ::= pf(a) }
    acc.reverse
  }

  /** maps from A to EMon[B], collects the good values. */
  def mapCollectGoods[B, BB <: SeqImut[B]](f: A => EMon[B])(implicit ev: ArrBuilder[B, BB]): BB =
  { val acc = ev.newBuff()
    foreach(f(_).forGood(ev.buffGrow(acc, _)))
    ev.buffToBB(acc)
  }

  def max[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    tailForeach{ el => acc = ord.max(acc, el) }
    acc
  }

  def min[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    tailForeach{ el => acc = ord.min(acc, el) }
    acc
  }

  /** Gives the maximum value of type B, produced by applying the function from A to B on each element of this collection, or the default value if the
   *  collection is empty. */
  def fMax[B](defaultValue: B)(f: (A) => B)(implicit cmp: math.Ordering[B]): B = if (empty) defaultValue else
  { var acc = f(head)
    tailForeach{ el => acc = cmp.max(acc, f(el)) }
    acc
  }

  /** Gives the minimum value of type B, produced by applying the function from A to B on each element of this collection, or the default value if the
   *  collection is empty. */
  def fMin[B](defaultValue: B)(f: (A) => B)(implicit cmp: math.Ordering[B]): B =
  { var acc = f(head)
    tailForeach{ el => acc = cmp.min(acc, f(el)) }
    acc
  }

  def toStrsCommaFold(fToStr: A => String): String = toStrsFold(", ", fToStr)
  def toStrsCommaNoSpaceFold(fToStr: A => String): String = toStrsFold(",", fToStr)
  def toStrsSemiFold(fToStr: A => String): String = toStrsFold("; ", fToStr)
  def toStrsCommaParenth(fToStr: A => String): String = toStrsCommaFold(fToStr).enParenth
  def toStrsSemiParenth(fToStr: A => String): String = toStrsSemiFold(fToStr).enParenth

  def sum(implicit ev: Sumable[A] @uncheckedVariance): A = foldLeft[A](ev.identity)(ev.sum(_, _))
}

case class ArrayLikeShow[A, R <: SeqGen[A]](evA: ShowT[A]) extends ShowTSeqLike[A, R]
{
  override def syntaxDepthT(obj: R): Int = obj.fMax(1)(evA.syntaxDepthT(_))
  override def showT(obj: R, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ""
}