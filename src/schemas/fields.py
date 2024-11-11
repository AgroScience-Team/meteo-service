import uuid

from pydantic import BaseModel


class FieldSchema(BaseModel):
    id: uuid.UUID
    longitude: float
    latitude: float
    parse_meteo: bool = True
