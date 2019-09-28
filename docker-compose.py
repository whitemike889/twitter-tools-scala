import json
import os


def get_mode():
    if os.environ["DC_ENV"] != "prod" and os.environ["DC_ENV"] != "dev" and os.environ["DC_ENV"] != "test":
        raise Exception()
    return os.environ["DC_ENV"]


def compose(mode):
    return {
        "version": "3",
        "services": compose_services(mode)
    }


def compose_services(mode):
    return {
        "app": compose_services_app(mode),
        "psql": compose_services_psql(mode)
    }


def compose_services_app(mode):
    service = {
        "build": "." if mode != "dev" else {
            "context": ".", "dockerfile": "dev.Dockerfile"},
        "restart": "always",
        "environment": {
            "APP_DIR": "data" if mode != "dev" else "data/app",
            "PSQL_HOST": "psql",
            "PSQL_PORT": 5432,
            "PSQL_DATABASE": "postgres",
            "PSQL_USER": "postgres",
            "PSQL_PASS": "postgres"
        },
        "depends_on": ["psql"],
    }
    if mode == "prod":
        service["volumes"] = ["./data/app:/home/app/data"]
    if mode == "dev":
        service["volumes"] = ["./:/home/app/"]

    return service


def compose_services_psql(mode):
    return {
        "restart": "always",
        "image": "postgres:10.5",
        "environment": {
            "POSTGRES_PASSWORD": "postgres"
        },
        "volumes": [
            "./data/psql:/var/lib/postgresql/data"
        ]
    }


print(json.dumps(compose(get_mode())))
