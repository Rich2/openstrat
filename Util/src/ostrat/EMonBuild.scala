package ostrat

trait EMonBuild[B, EMonT <: EMonBase[B]]
{
  //type EMonT <: EMonBase[B]
  type GoodT <: EMonT with GoodBase[B]
  type BadT <: EMonT with BadBase[B]
  def apply(value: B): GoodT
  def newBad(errs: Refs[String]): BadT
}

object EMonBuild
{
  implicit def refImplicit[B <: AnyRef]: EMonBuild[B, EMon[B]] = new EMonBuild[B, EMon[B]]
  { override type GoodT = Good[B]
    override type BadT = Bad[B]
    override def apply(value: B): Good[B] = Good(value)
    override def newBad(errs: Refs[String]): Bad[B] = new Bad(errs)
  }

  implicit val intImplicit: EMonBuild[Int, EMonInt] = new EMonBuild[Int, EMonInt]
  { override type GoodT = GoodInt
    override type BadT = BadInt
    override def apply(value: Int): GoodInt = GoodInt(value)
    override def newBad(errs: Refs[String]): BadInt = new BadInt(errs)
  }
}