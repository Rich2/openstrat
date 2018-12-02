// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._

trait Common extends ScalaModule
{
  def version = "0.0.2snap"  
  def scalacOptions = Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint")
}

trait PlatformsModule extends ScalaModule with Common
{
  outer =>
  
  def scalaVersion = "2.12.7"
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm)

  trait InnerJs extends ScalaJSModule with Common
  { 
  	def scalaVersion = "2.12.7"
	  def scalaJSVersion = "0.6.26" 
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcJs)
	  
	  def ivyDeps: mill.define.Target[mill.util.Loose.Agg[mill.scalalib.Dep]] =
	   outer.ivyDeps() ++ ivyJs() ++  Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.6")
	  
	  def ivyJs: mill.define.Target[mill.util.Loose.Agg[mill.scalalib.Dep]] = Agg[mill.scalalib.Dep]()
  }

  trait InnerNative extends ScalaNativeModule with Common
  {
    def scalaVersion = "2.11.12"
    def scalaNativeVersion = "0.3.8"  
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcNat)
	  def ivyDeps: mill.define.Target[mill.util.Loose.Agg[mill.scalalib.Dep]] = outer.ivyDeps() ++ ivyNat()
	  def ivyNat: mill.define.Target[mill.util.Loose.Agg[mill.scalalib.Dep]] = Agg[mill.scalalib.Dep]()
  }
}

object Macros extends PlatformsModule
{ def ivyDeps: mill.define.Target[mill.util.Loose.Agg[mill.scalalib.Dep]] = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
  object Js extends InnerJs
  object Nat extends InnerNative  
}

object Graphic extends PlatformsModule
{ 
  def moduleDeps = Seq(Macros)
  
  object test extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.6")
    def testFrameworks = Seq("utest.runner.Framework")    
  }
  object Js extends InnerJs
  {
    def moduleDeps = Seq(Macros.Js)    
  }
  object Nat extends InnerNative
}

object Core extends PlatformsModule
{
   def moduleDeps = Seq(Graphic)
  
  object test extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.6")
    def testFrameworks = Seq("utest.runner.Framework")
    //def moduleDeps = Seq(Graphic.test, Core)   
  }
   object Js extends InnerJs
  {
    def moduleDeps = Seq(Graphic.Js)    
  }
  object Nat extends InnerNative
}

def run() = Core.runBackground()
def test = Core.test
def jsfast = Core.Js.fastOpt
def jsfull = Core.Js.fullOpt

