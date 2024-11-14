import uuid

from sqlalchemy import Boolean, Float, Uuid
from sqlalchemy.orm import Mapped, mapped_column

from src.models.base import Base


class Field(Base):
    id: Mapped[uuid.UUID] = mapped_column(Uuid, primary_key=True, default=uuid.uuid4)
    longitude: Mapped[float] = mapped_column(Float)
    latitude: Mapped[float] = mapped_column(Float)
    parse_meteo: Mapped[bool] = mapped_column(Boolean)
