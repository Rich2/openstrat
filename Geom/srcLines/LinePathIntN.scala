/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._

trait LinePathIntN[VT <: IntNElem] extends  Any with LinePathLike[VT] with SeqSpecIntN[VT]
{ type ThisT <: LinePathIntN[VT]
  type PolygonT <: PolygonLikeIntN[VT]

  /** Constructs a [[PolygonLike]] for this vertex type from an [[Array]][Int]. */
  def polygonFromArray(array: Array[Int]): PolygonT

  override def tail: ThisT =
  { val newLen = (numVerts - 1).max0
    val newArrayLen = newLen * elemProdSize
    val newArray: Array[Int] = new Array[Int](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    while(i < newLen)
    { res.setElemUnsafe(i, elem(i + 1))
      i += 1
    }
    res
  }

  override def init: ThisT =
  { val newLen = (numVerts - 1).max0
    val newArrayLen = newLen * elemProdSize
    val newArray: Array[Int] = new Array[Int](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    while(i < newLen)
    { res.setElemUnsafe(i, elem(i))
      i += 1
    }
    res
  }

  override def inner: ThisT =
  { val newArrayLen = (numVerts - 2).max0 * elemProdSize
    val newArray = new Array[Int](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    innerForeach { el => res.setElemUnsafe(i, el); i += 1 }
    res
  }

  @targetName("append") final override def ++(operand: ThisT): ThisT = fromArray(arrayUnsafe ++ operand.arrayUnsafe)

  def appendInitArray(opArray: Array[Int]): Array[Int] =
  { val deltaLen: Int = (opArray.length - elemProdSize).max0
    val newLen = arrayLen + deltaLen
    val newArray = new Array[Int](newLen)
    arrayUnsafe.copyToArray(newArray)
    Array.copy(opArray, 0, newArray, arrayLen, deltaLen )
    newArray
  }

  def initAppendInitArray(opArray: Array[Int]): Array[Int] =
  { val init1Len: Int = (arrayLen - elemProdSize).max0
    val init2Len: Int = (opArray.length - elemProdSize).max0
    val newLen: Int = init1Len + init2Len
    val newArray = new Array[Int](newLen)
    arrayUnsafe.copyToArray(newArray, 0, init1Len)
    Array.copy(opArray, 0, newArray, init1Len, init2Len)
    newArray
  }

  @targetName("appendTail") override def +-+(operand: ThisT): ThisT =
  { val deltaLen: Int = (operand.numVerts - 1).max0
    val newLen = numVerts + deltaLen
    val newArray = new Array[Int](newLen * elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    Array.copy(operand.arrayUnsafe, 0, newArray, arrayLen, deltaLen * elemProdSize)
    fromArray(newArray)
  }

  @targetName("appendPt") @inline final override def +%(operandPt: VT): ThisT = fromArray(appendPtToArray(operandPt))
  @targetName("appendPtToPolygon") final override def |+%|(operandPt: VT): PolygonT = polygonFromArray(appendPtToArray(operandPt))
  
  def appendPtToArray(pt: VT): Array[Int] =
  { val newArray = new Array[Int](arrayLen + elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    var i = arrayLen
    pt.intForeach { ii =>
      newArray(i) = ii
      i += 1
    }
    newArray
  }

  @targetName("prepend") @inline final override def %:(operand: VT): ThisT =
  { val newArray = new Array[Int](arrayLen + elemProdSize)
    Array.copy(arrayUnsafe, 0, newArray, elemProdSize, arrayLen)
    var i = 0
    operand.intForeach { j => newArray(i) = j; i += 1 }
    fromArray(newArray)
  }

  @targetName("prependReverse") @inline final override def %<:(operand: VT): ThisT =
  { val newArray = new Array[Int](arrayLen + elemProdSize)
    var i = 0
    operand.intForeach { d => newArray(i) = d; i += 1 }
    val res = fromArray(newArray)
    i = 1
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }


  @targetName("appendReverse") final override def ++<(operand: ThisT): ThisT = {
    val newArray = new Array[Int](arrayLen + operand.arrayLen)
    arrayUnsafe.copyToArray(newArray)
    val res = fromArray(newArray)
    var i = numElems
    operand.reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }

  @inline override def toPolygon: PolygonT = polygonFromArray(arrayUnsafe)
  @targetName("appendToPolygon") @inline override def |++|(operand: ThisT): PolygonT = polygonFromArray(arrayUnsafe ++ operand.arrayUnsafe)
  @targetName("appendTailToPolygon") final override  def |+-+|(operand: ThisT): PolygonT = polygonFromArray(appendInitArray(operand.arrayUnsafe))
  @targetName("initAppendInitToPolygon") final override  def |-++-|(operand: ThisT): PolygonT = polygonFromArray(initAppendInitArray(operand.arrayUnsafe))

  @targetName("appendVertToPolygon") override def |+|[AA >: VT](op: VT): PolygonT =
  { val newArray = new Array[Int](arrayLen + elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    var i = arrayLen
    op.intForeach { ii =>
      newArray(i) = ii
      i += 1
    }
    polygonFromArray(newArray)
  }

  @targetName("appendReverseToPolygon") final override def |++<|(operand: ThisT): PolygonT =
    polygonFromArray((this ++< operand).arrayUnsafe)

  @targetName("reverseAppend") final override def +<+(operand: ThisT): ThisT =
  { val newArray = new Array[Int](arrayLen + operand.arrayLen)
    val res = fromArray(newArray)
    var i = 0
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    Array.copy(operand.arrayUnsafe, 0, newArray, arrayLen, operand.arrayLen)
    res
  }

  @targetName("reverseAppendToPolygon") final override def |+<+|(operand: ThisT): PolygonT =
  { val newArray = new Array[Int](arrayLen + operand.arrayLen)
    val res = polygonFromArray(newArray)
    var i = 0
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    Array.copy(operand.arrayUnsafe, 0, newArray, arrayLen, operand.arrayLen)
    res
  }

  @targetName("reverseAppendReverse") final override def +<+<(operand: ThisT): ThisT =
  { val newArray = new Array[Int](arrayLen + operand.arrayLen)
    val res = fromArray(newArray)
    var i = 0
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    operand.reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }
}

trait LinePathInt2[VT <: Int2Elem] extends Any with LinePathIntN[VT] with SeqSpecInt2[VT]
{ type ThisT <: LinePathInt2[VT]
  type PolygonT <: PolygonLikeInt2[VT]
}

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait LinePathBuilder[B, BB <: LinePathLike[B]] extends BuilderMapSeqLike[B, BB]

/** Trait for creating the line path builder instances for the [[LinePathBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait LinePathBuilderValueN[B <: ValueNElem, BB <: LinePathLike[B]] extends LinePathBuilder[B, BB] with BuilderSeqLike[BB]

/** Trait for creating the builder type class instances for [[LinePathDblN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathMapBuilderDblN[B <: DblNElem, BB <: LinePathDblN[B] ] extends LinePathBuilderValueN[B, BB] with BuilderMapSeqLikeDblN[B, BB]

/** Trait for creating the line path type class instances for [[LinePathDbl2]] final classes. Instances for the [[LinePathDbl2Builder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl2Builder[B <: Dbl2Elem, BB <: LinePathDbl2[B]] extends LinePathMapBuilderDblN[B, BB] with BuilderMapSeqLikeDbl2[B, BB]

/** Trait for creating the line path type class instances for [[LinePathDbl3]] final classes. Instances for the [[LinePathDbl3MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl3MapBuilder[B <: Dbl3Elem, BB <: LinePathDbl3[B]] extends LinePathMapBuilderDblN[B, BB] with BuilderMapSeqLikeDbl3[B, BB]

/** Trait for creating the builder type class instances for [[LinePathIntN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathIntNMapBuilder[B <: IntNElem, BB <: LinePathIntN[B] ] extends LinePathBuilderValueN[B, BB] with BuilderSeqLikeIntNMap[B, BB]

/** Trait for creating the line path type class instances for [[LinePathInt2]] final classes. Instances for the [[LinePathInt2MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathInt2MapBuilder[B <: Int2Elem, BB <: LinePathInt2[B]] extends LinePathIntNMapBuilder[B, BB] with BuilderMapSeqLikeInt2[B, BB]