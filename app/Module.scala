import play.api.inject.Binding
import play.api.{Configuration, Environment, inject}

class Module extends inject.Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] =
    Seq(bind[Database].toSelf.eagerly())
}
