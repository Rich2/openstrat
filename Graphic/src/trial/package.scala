package object trial {
  
  implicit def transToTranser[T <: Transer](value: T) =
  {
    val ev = new Trans[T] {
      override def trans(obj: T, f: Int => Int): T = obj.fTrans(f).asInstanceOf[T]
    }    
    new TransBase[T](value, ev) 
  }
  
  implicit def avonToTranser[T](value: T)(implicit ev: Trans[T]) = new TransBase[T](value, ev)  
}