import json
import os


def get_mode():
    if os.environ["DC_ENV"] != "prod" and os.environ["DC_ENV"] != "dev" and os.environ["DC_ENV"] != "test":
        raise Exception()
    return os.environ["DC_ENV"]


def compose(mode):
    compose = {}
    compose["version"] = "3"
    compose["services"] = compose_services(mode)
    return compose


def compose_services(mode):
    services = {}
    services["app"] = compose_services_app(mode)
    services["psql"] = compose_services_psql(mode)
    return services


def compose_services_app(mode):
    service = {}
    service["build"] = "." if mode != "dev" else {
        "context": ".", "dockerfile": "dev.Dockerfile"}
    service["restart"] = "always"
    service["environment"] = {
        "APP_DIR": "data" if mode != "dev" else "data/app",
        "PSQL_HOST": "psql",
        "PSQL_PORT": 5432,
        "PSQL_DATABASE": "postgres",
        "PSQL_USER": "postgres",
        "PSQL_PASS": "postgres"
    }
    service["depends_on"] = ["psql"]
    if mode == "prod":
        service["volumes"] = ["./data/app:/home/app/data"]
    if mode == "dev":
        service["volumes"] = ["./:/home/app/"]

    return service


def compose_services_psql(mode):
    service = {}
    service["restart"] = "always"
    service["image"] = "postgres:10.5"
    service["environment"] = {
        "POSTGRES_PASSWORD": "postgres"
    }
    service["volumes"] = [
        "./data/psql:/var/lib/postgresql/data"
    ]

    return service


print(json.dumps(compose(get_mode())))
