import {Image} from "./image.model";
import {Attachment} from "./attachment.model";

export class Offer {
  id: number;
  title: string;
  description: string;
  author: string;
  createdDate: string;
  updateDate: string;
  images: Image [];
  attachments: Attachment [];
}
