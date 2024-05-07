from datetime import datetime

from sqlalchemy import DateTime, Float, ForeignKey, Integer
from sqlalchemy.orm import Mapped, mapped_column

from src.models.base import Base


class MeteoData(Base):
    __tablename__ = "meteo_data"
    
    id: Mapped[int] = mapped_column(primary_key=True)
    field_id: Mapped[int] = mapped_column(Integer, ForeignKey("fields.id"), index=True)
    date_time: Mapped[datetime] = mapped_column(DateTime)
    
    temperature: Mapped[float] = mapped_column(Float, nullable=True)
    humidity: Mapped[float] = mapped_column(Float, nullable=True)
    wind_speed: Mapped[float] = mapped_column(Float, nullable=True)
    precipitation: Mapped[float] = mapped_column(Float, nullable=True)
    dew_point: Mapped[float] = mapped_column(Float, nullable=True)
    soil_temperature_0cm: Mapped[float] = mapped_column(Float, nullable=True)
    soil_temperature_6cm: Mapped[float] = mapped_column(Float, nullable=True)
    soil_temperature_18cm: Mapped[float] = mapped_column(Float, nullable=True)
    soil_moisture_0_to_1cm: Mapped[float] = mapped_column(Float, nullable=True)
    soil_moisture_1_to_3cm: Mapped[float] = mapped_column(Float, nullable=True)
    soil_moisture_3_to_9cm: Mapped[float] = mapped_column(Float, nullable=True)
    soil_moisture_9_to_27cm: Mapped[float] = mapped_column(Float, nullable=True)
    
    temperature_max: Mapped[float] = mapped_column(Float, nullable=True)
    temperature_min: Mapped[float] = mapped_column(Float, nullable=True)
    sunrise: Mapped[datetime] = mapped_column(DateTime, nullable=True)
    sunset: Mapped[datetime] = mapped_column(DateTime, nullable=True)
    precipitation_sum: Mapped[float] = mapped_column(Float, nullable=True)
